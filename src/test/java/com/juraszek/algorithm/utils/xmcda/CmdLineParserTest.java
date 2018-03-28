package com.juraszek.algorithm.utils.xmcda;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CmdLineParserTest {

    @Test
    public void parseCmdLineArgumentsSuccessVersion3() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"--input-directory", "data/successExamplesV3/example1-input",
                "--output-directory", "data/successExamplesV3/example1-output", "--v3"};

        final CmdLineParser.Arguments EXPECTED_ARGUMENTS = new CmdLineParser.Arguments();
        EXPECTED_ARGUMENTS.inputDirectory = "data/successExamplesV3/example1-input";
        EXPECTED_ARGUMENTS.outputDirectory = "data/successExamplesV3/example1-output";
        EXPECTED_ARGUMENTS.xmcdaVersion = Version.v3;

        CmdLineParser cmdLineParser = new CmdLineParser();

        //when
        final CmdLineParser.Arguments result = cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then
        assertThat(result.inputDirectory).isEqualTo(EXPECTED_ARGUMENTS.inputDirectory);
        assertThat(result.outputDirectory).isEqualTo(EXPECTED_ARGUMENTS.outputDirectory);
        assertThat(result.xmcdaVersion).isEqualTo(EXPECTED_ARGUMENTS.xmcdaVersion);
    }

    @Test
    public void parseCmdLineArgumentsSuccessVersion2() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"--input-directory", "data/successExamplesV3/example1-input",
                "--output-directory", "data/successExamplesV3/example1-output", "--v2"};

        final CmdLineParser.Arguments EXPECTED_ARGUMENTS = new CmdLineParser.Arguments();
        EXPECTED_ARGUMENTS.inputDirectory = "data/successExamplesV3/example1-input";
        EXPECTED_ARGUMENTS.outputDirectory = "data/successExamplesV3/example1-output";
        EXPECTED_ARGUMENTS.xmcdaVersion = Version.v2;

        CmdLineParser cmdLineParser = new CmdLineParser();

        //when
        final CmdLineParser.Arguments result = cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then
        assertThat(result.inputDirectory).isEqualTo(EXPECTED_ARGUMENTS.inputDirectory);
        assertThat(result.outputDirectory).isEqualTo(EXPECTED_ARGUMENTS.outputDirectory);
        assertThat(result.xmcdaVersion).isEqualTo(EXPECTED_ARGUMENTS.xmcdaVersion);
    }

    @Test
    public void parseCmdLineArgumentsSuccessVersion3V2() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamplesV3/example1-input",
                "-o", "data/successExamplesV3/example1-output","--v3"};

        final CmdLineParser.Arguments EXPECTED_ARGUMENTS = new CmdLineParser.Arguments();
        EXPECTED_ARGUMENTS.inputDirectory = "data/successExamplesV3/example1-input";
        EXPECTED_ARGUMENTS.outputDirectory = "data/successExamplesV3/example1-output";
        EXPECTED_ARGUMENTS.xmcdaVersion = Version.v3;

        CmdLineParser cmdLineParser = new CmdLineParser();

        //when
        final CmdLineParser.Arguments result = cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then
        assertThat(result.inputDirectory).isEqualTo(EXPECTED_ARGUMENTS.inputDirectory);
        assertThat(result.outputDirectory).isEqualTo(EXPECTED_ARGUMENTS.outputDirectory);
        assertThat(result.xmcdaVersion).isEqualTo(EXPECTED_ARGUMENTS.xmcdaVersion);

    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsWrongOrder() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"data/successExamplesV3/example1-input", "-i",
                "-o", "data/successExamplesV3/example1-output", "--v3"};

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsTooManyArguments() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamplesV3/example1-input",
                "-o", "data/successExamplesV3/example1-output", "--v2", "--v3"};

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsNotEnoughArguments() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamplesV3/example1-input"
        };

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }
}