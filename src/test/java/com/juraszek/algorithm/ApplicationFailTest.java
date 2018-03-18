package com.juraszek.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(Parameterized.class)
public class ApplicationFailTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private String input;
    private String output;
    private String errorMessage;

    public ApplicationFailTest(String input, String output, String errorMessage) {
        this.input = input;
        this.output = output;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"example1-fail-input", "example1-fail-output", "Missing input files."}, // without criteriaMatrix.xml
                {"example2-fail-input", "example2-fail-output", "Invalid inputs format."}, // no double type for pairs
                {"example3-fail-input", "example3-fail-output", "Invalid inputs format."}, // pairs with more than one value
                {"example4-fail-input", "example4-fail-output",
                        "The calculation could not be performed, reason: Can not propose values for pairs comparisons"}, // concepts without reachability
                {"example5-fail-input", "example5-fail-output", "Invalid inputs format."}, // wrong parameter passed
                {"example6-fail-input", "example6-fail-output", "The calculation could not be performed, reason: Invalid calculation method: Arithmeticf"} // invalid calculation method
        });
    }

    @Test
    public void invalidInputCriteriaMatrixMissingTest() throws IOException {
        //given
        String TMP_OUTPUT_PATH = folder.newFolder(output).getAbsolutePath();
        String INPUT_PATH = "src/test/resources/data/failsExamples/" + input;
        String EXPECTED_OUTPUT_PATH = "src/test/resources/data/failsExamples/" + output;
        File EXPECTED_MESSAGES = new File(EXPECTED_OUTPUT_PATH + "/messages.xml");

        final String[] GIVEN_PARAMS = {"--input-directory", INPUT_PATH,
                "--output-directory", TMP_OUTPUT_PATH};

        try {
            Application.main(GIVEN_PARAMS);
            fail("Should have thrown Exception but did not!");
        } catch (Throwable throwable) {
            assertThat(throwable.getMessage()).isEqualTo(errorMessage);
        }

        //then
        File messages = new File(TMP_OUTPUT_PATH + "/messages.xml");

        assertThat(FileUtils.contentEquals(messages, EXPECTED_MESSAGES)).isTrue();
    }

}