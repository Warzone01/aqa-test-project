package api.demoqa;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class DemoQaRetryFilter implements Filter {

    private static final int MAX_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MILLIS = 1_000L;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                return ctx.next(requestSpec, responseSpec);
            } catch (RuntimeException exception) {
                lastException = exception;
                if (!isNetworkTimeout(exception) || attempt == MAX_ATTEMPTS) {
                    throw exception;
                }
                waitBeforeRetry();
            }
        }

        throw lastException;
    }

    private boolean isNetworkTimeout(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof ConnectException || current instanceof SocketTimeoutException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    private void waitBeforeRetry() {
        try {
            Thread.sleep(RETRY_DELAY_MILLIS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Повторный запрос DemoQA был прерван", exception);
        }
    }
}
