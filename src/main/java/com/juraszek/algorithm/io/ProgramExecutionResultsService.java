package com.juraszek.algorithm.io;

import com.juraszek.algorithm.utils.xmcda.Version;
import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import org.springframework.stereotype.Service;
import org.xmcda.Message;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;
import org.xmcda.converters.v2_v3.XMCDAConverter;
import org.xmcda.parsers.xml.xmcda_v2.XMCDAParser;

import java.io.File;

@Service
public class ProgramExecutionResultsService {

    public void writeProgramExecutionResults(File prgExecResultsFile, ProgramExecutionResult errors, Version version)
            throws Throwable {
        org.xmcda.parsers.xml.xmcda_v3.XMCDAParser parser = new org.xmcda.parsers.xml.xmcda_v3.XMCDAParser();

        XMCDA prgExecResults = new XMCDA();
        prgExecResults.programExecutionResultsList.add(errors);

        switch (version)
        {
            case v3:
                parser.writeXMCDA(prgExecResults, prgExecResultsFile, "programExecutionResult");
                break;
            case v2:
                if(errors.isOk()) {
                    ProgramExecutionResult messages = new ProgramExecutionResult();
                    messages.add(new Message(Message.Level.INFO, "ok"));
                    prgExecResults.programExecutionResultsList.clear();
                    prgExecResults.programExecutionResultsList.add(messages);
                }
                org.xmcda.v2.XMCDA xmcda_v2 = XMCDAConverter.convertTo_v2(prgExecResults);
                XMCDAParser.writeXMCDA(xmcda_v2, prgExecResultsFile, "methodMessages");
                break;
            default:
                // in case the enum has some more values in the future and the new ones have not been added here
                throw new IllegalArgumentException("Unhandled XMCDA version " + version.toString());
        }
    }

    public void writeProgramExecutionResultsAndExit(File prgExecResultsFile, ProgramExecutionResult errors, String message, Version version) throws Exception {
        try {
            writeProgramExecutionResults(prgExecResultsFile, errors, version);
        } catch (Throwable t) {
            System.err.println(XMCDAMessageParser.getMessage("Could not write messages.xml, reason: ", t));
            System.exit(ProgramExecutionResult.Status.ERROR.exitStatus());
        }
        throw new Exception(message);
    }


}
