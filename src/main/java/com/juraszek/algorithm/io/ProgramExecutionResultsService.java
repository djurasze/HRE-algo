package com.juraszek.algorithm.io;

import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;

import java.io.File;

@Service
public class ProgramExecutionResultsService {

    public void writeProgramExecutionResults(File prgExecResultsFile, ProgramExecutionResult errors)
            throws Throwable {
        org.xmcda.parsers.xml.xmcda_v3.XMCDAParser parser = new org.xmcda.parsers.xml.xmcda_v3.XMCDAParser();

        XMCDA prgExecResults = new XMCDA();
        prgExecResults.programExecutionResultsList.add(errors);
        parser.writeXMCDA(prgExecResults, prgExecResultsFile, "programExecutionResult");
    }

    public void writeProgramExecutionResultsAndExit(File prgExecResultsFile, ProgramExecutionResult errors, String message) throws Exception {
        try {
            writeProgramExecutionResults(prgExecResultsFile, errors);
        } catch (Throwable t) {
            System.err.println(XMCDAMessageParser.getMessage("Could not write messages.xml, reason: ", t));
            System.exit(ProgramExecutionResult.Status.ERROR.exitStatus());
        }
        throw new Exception(message);
    }


}
