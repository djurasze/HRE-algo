package com.juraszek.algorithm.io;

import com.juraszek.algorithm.io.parsers.Parser;
import com.juraszek.algorithm.utils.xmcda.Version;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;

import java.io.File;

@Service
public class DataLoader {

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    private Parser parser;

    public XMCDA loadXMCDA(String inputDirectory,
                          ProgramExecutionResult executionResult) {
        return parser.loadXMCDA(inputDirectory, executionResult);
    }
}
