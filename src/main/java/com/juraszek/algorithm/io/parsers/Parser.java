package com.juraszek.algorithm.io.parsers;

import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;

import java.io.File;

public interface Parser {
    XMCDA loadXMCDA(String inputDirectory,
                            ProgramExecutionResult executionResult);
}
