package com.sdet.builder.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Framework Hardening")
@Feature("Reporting")
@Owner("Bhumika")
public class AllureReportingTest {

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that categories.json is configured correctly.")
    void verifyCategoriesConfiguration() throws IOException {

        Allure.step("Reading categories.json file");

        String categories = Files.readString(
                Path.of("src/test/resources/allure/categories.json")
        );

        Allure.step("Verifying category order");

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int brokenIndex = categories.indexOf("\"Test defects (broken)\"");
        int productIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex >= 0);
        assertTrue(brokenIndex > flakyIndex);
        assertTrue(productIndex > brokenIndex);

        Allure.addAttachment(
                "Categories File",
                categories
        );
    }

    @Test
    @Story("Product Failure")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional failure to demonstrate Product Defect category.")

    void productFailure() {

        assertEquals(10,20,"Incorrect Cart Total");
    }

    @Test
    @Story("Broken Test")
    @Severity(SeverityLevel.NORMAL)
    @Description("Intentional broken test to demonstrate Broken category.")

    void brokenTest() {

        String text = null;

        text.length();
    }

    @Test
    @Disabled("Demonstration of skipped tests")
    @Story("Skipped Test")
    @Severity(SeverityLevel.MINOR)

    void skippedTest() {

    }

}