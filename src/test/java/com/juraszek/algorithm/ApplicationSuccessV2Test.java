package com.juraszek.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(Parameterized.class)
public class ApplicationSuccessV2Test {

    private final static String VERSION = "--v2";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private String input;
    private String output;
    public ApplicationSuccessV2Test(String input, String output) {
        this.input = input;
        this.output = output;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { "example1-input","example1-output"}, // simple judgements, arithmetic
                {"example2-input", "example2-output"}, // with reference concept values, arithmetic
                { "example3-input","example3-output"}, // not reciprocal matrix, arithmetic
                { "example4-input","example4-output"},  // incomplete matrix, arithmetic
                { "example5-input","example5-output"},  // scientific entities assessment, geometric
                { "example6-input","example6-output"}, // tv shows, geometric
                { "example-inconsistency1-input","example-inconsistency1-output"}, // arithmetic
                { "example-inconsistency2-input","example-inconsistency2-output"}, // MinimizingEstimationError
                { "example-inconsistency3-input","example-inconsistency3-output"}, // geometric
                { "example-inconsistency4-input","example-inconsistency4-output"} // smallest error method

        });
    }

    @Test
    public void examples() throws Throwable {
        //given
        String TMP_OUTPUT_PATH = folder.newFolder(output).getAbsolutePath();
        String INPUT_PATH = "src/test/resources/data/successExamplesV2/" + input;
        String EXPECTED_OUTPUT_PATH = "src/test/resources/data/successExamplesV2/" + output;
        File EXPECTED_ALTERNATIVES_VALUES = new File(EXPECTED_OUTPUT_PATH + "/alternativesValues.xml");
        File EXPECTED_MESSAGES = new File(EXPECTED_OUTPUT_PATH + "/messages.xml");

        final String[] GIVEN_PARAMS = {"--input-directory", INPUT_PATH,
                "--output-directory", TMP_OUTPUT_PATH, VERSION};


        //when
        Application.main(GIVEN_PARAMS);

        //then
        File alternativesValues = new File(TMP_OUTPUT_PATH + "/alternativesValues.xml");
        File messages = new File(TMP_OUTPUT_PATH + "/messages.xml");

        assertThat(FileUtils.contentEqualsIgnoreEOL(alternativesValues, EXPECTED_ALTERNATIVES_VALUES, "utf-8")).isTrue();
        assertThat(FileUtils.contentEqualsIgnoreEOL(messages, EXPECTED_MESSAGES, "utf-8")).isTrue();

    }

}
