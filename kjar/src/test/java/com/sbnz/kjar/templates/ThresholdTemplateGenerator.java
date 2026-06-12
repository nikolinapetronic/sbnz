package com.sbnz.kjar.templates;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThresholdTemplateGenerator {

    private static final Path TEMPLATE_PATH = Path.of(
        "kjar/src/main/resources/templates/profile-thresholds.drt"
    );

    private static final Path GENERATED_RULES_PATH = Path.of(
            "kjar/src/main/resources/rules/10_profile_thresholds.drl"
    );

    public static void main(String[] args) throws Exception {
        String[][] thresholdData = new String[][]{
                {"DEFAULT", "REGULAR_CLASSES", "6", "7", "4", "2", "5", "5"},
                {"DEFAULT", "MIDTERM_WEEK", "6", "7", "4", "2", "5", "5"},
                {"DEFAULT", "EXAM_PERIOD", "6", "7", "5", "2", "6", "5"},
                {"DEFAULT", "AFTER_EXAM_PERIOD", "6", "7", "4", "2", "5", "5"},

                {"FRESHMAN", "REGULAR_CLASSES", "6", "7", "4", "2", "6", "5"},
                {"FRESHMAN", "MIDTERM_WEEK", "6", "7", "4", "2", "6", "5"},
                {"FRESHMAN", "EXAM_PERIOD", "6", "7", "5", "2", "6", "5"},
                {"FRESHMAN", "AFTER_EXAM_PERIOD", "6", "7", "4", "2", "6", "5"},

                {"WORKING_STUDENT", "REGULAR_CLASSES", "5", "8", "4", "3", "7", "4"},
                {"WORKING_STUDENT", "MIDTERM_WEEK", "5", "8", "4", "3", "7", "4"},
                {"WORKING_STUDENT", "EXAM_PERIOD", "5", "8", "5", "3", "7", "4"},
                {"WORKING_STUDENT", "AFTER_EXAM_PERIOD", "5", "8", "4", "3", "7", "4"},

                {"FINAL_YEAR", "REGULAR_CLASSES", "6", "7", "4", "2", "5", "4"},
                {"FINAL_YEAR", "MIDTERM_WEEK", "6", "7", "4", "2", "5", "4"},
                {"FINAL_YEAR", "EXAM_PERIOD", "6", "7", "5", "2", "5", "4"},
                {"FINAL_YEAR", "AFTER_EXAM_PERIOD", "6", "7", "4", "2", "5", "4"}
        };

        DataProvider dataProvider = new ArrayDataProvider(thresholdData);
        DataProviderCompiler compiler = new DataProviderCompiler();

        try (InputStream template = Files.newInputStream(TEMPLATE_PATH)) {
            String generatedDrl = compiler.compile(dataProvider, template);

            Files.createDirectories(GENERATED_RULES_PATH.getParent());
            Files.writeString(GENERATED_RULES_PATH, generatedDrl, StandardCharsets.UTF_8);

            System.out.println("Generated threshold rules:");
            System.out.println(GENERATED_RULES_PATH.toAbsolutePath());
        }
    }
}
