package tests;

import org.junit.jupiter.api.Test;
import pages.WebTablesPage;

public class WebTablesTest extends BaseTest {

    private final WebTablesPage webTablesPage = new WebTablesPage();

    @Test
    void clickButton() {
        webTablesPage
                .openPage()
                .addNewRecord();
    }
}
