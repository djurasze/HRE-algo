package com.juraszek.algorithm.utils.xmcda;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CmdLineParserTest {

    @Test
    public void parseCmdLineArgumentsSuccess() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"--input-directory", "data/successExamples/example1-input",
                "--output-directory", "data/successExamples/example1-output"};

        final CmdLineParser.Arguments EXPECTED_ARGUMENTS = new CmdLineParser.Arguments();
        EXPECTED_ARGUMENTS.inputDirectory = "data/successExamples/example1-input";
        EXPECTED_ARGUMENTS.outputDirectory = "data/successExamples/example1-output";

        CmdLineParser cmdLineParser = new CmdLineParser();

        //when
        final CmdLineParser.Arguments result = cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then
        assertThat(result.inputDirectory).isEqualTo(EXPECTED_ARGUMENTS.inputDirectory);
        assertThat(result.outputDirectory).isEqualTo(EXPECTED_ARGUMENTS.outputDirectory);
    }

    @Test
    public void parseCmdLineArgumentsSuccessV2() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamples/example1-input",
                "-o", "data/successExamples/example1-output"};

        final CmdLineParser.Arguments EXPECTED_ARGUMENTS = new CmdLineParser.Arguments();
        EXPECTED_ARGUMENTS.inputDirectory = "data/successExamples/example1-input";
        EXPECTED_ARGUMENTS.outputDirectory = "data/successExamples/example1-output";

        CmdLineParser cmdLineParser = new CmdLineParser();

        //when
        final CmdLineParser.Arguments result = cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then
        assertThat(result.inputDirectory).isEqualTo(EXPECTED_ARGUMENTS.inputDirectory);
        assertThat(result.outputDirectory).isEqualTo(EXPECTED_ARGUMENTS.outputDirectory);
    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsWrongOrder() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"data/successExamples/example1-input", "-i",
                "-o", "data/successExamples/example1-output"};

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsTooManyArguments() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamples/example1-input",
                "-o", "data/successExamples/example1-output", "--version"};

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }

    @Test(expected = CmdLineParser.InvalidCommandLineException.class)
    public void parseCmdLineArgumentsNotEnoughArguments() throws CmdLineParser.InvalidCommandLineException {
        //given
        final String[] GIVEN_PARAMS = {"-i", "data/successExamples/example1-input"
        };

        CmdLineParser cmdLineParser = new CmdLineParser();
        //when
        cmdLineParser.parseCmdLineArguments(GIVEN_PARAMS);
        //then

    }
}