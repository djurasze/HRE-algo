package com.juraszek.algorithm;

import com.juraszek.algorithm.io.IOService;
import com.juraszek.algorithm.io.ProgramExecutionResultsService;
import com.juraszek.algorithm.models.HREModel;
import com.juraszek.algorithm.utils.xmcda.CmdLineParser;
import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import com.juraszek.algorithm.utils.xmcda.XMCDAProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;

import java.io.File;

@Service
public class HRERunner {

    @Autowired
    private HREAlgorithm hreAlgorithm;
    @Autowired
    private IOService ioService;
    @Autowired
    private XMCDAProperties xmcdaProperties;
    @Autowired
    private CmdLineParser cmdLineParser;
    @Autowired
    private ProgramExecutionResultsService programExecutionResultsService;

    public void start(String[] args) throws Throwable {

        final CmdLineParser.Arguments params = cmdLineParser.parseCmdLineArguments(args);
        final File prgExecResults = new File(params.outputDirectory, "messages.xml");
        final ProgramExecutionResult executionResult = new ProgramExecutionResult();

        HREModel inputs = ioService.readInputs(params, executionResult, prgExecResults);
        xmcdaProperties.initHeuristicMethod(executionResult, prgExecResults);
        try {
            inputs = hreAlgorithm.singleCalculate(inputs);
        } catch (Throwable t) {
            String message = XMCDAMessageParser.getMessage("The calculation could not be performed, reason: ", t);
            executionResult.addError(message);
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, message);
        }


        ioService.writeOutput(params, inputs, executionResult, prgExecResults);
    }
}
