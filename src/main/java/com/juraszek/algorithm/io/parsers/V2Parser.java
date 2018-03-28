package com.juraszek.algorithm.io.parsers;

import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import org.xmcda.*;
import org.xmcda.converters.v2_v3.DescriptionConverter;
import org.xmcda.converters.v2_v3.QualifiedValueConverter;
import org.xmcda.converters.v2_v3.ScaleConverter;
import org.xmcda.converters.v2_v3.XMCDAConverter;
import org.xmcda.parsers.xml.xmcda_v2.XMCDAParser;
import org.xmcda.utils.Coord;
import org.xmcda.value.NA;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.Iterator;

import static com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser.getMessage;

public class V2Parser implements Parser {

    public XMCDA loadXMCDA(String inputDirectory,
                           ProgramExecutionResult executionResult) {


        org.xmcda.v2.XMCDA xmcda_v2 = new org.xmcda.v2.XMCDA();
        loadXMCDA(xmcda_v2, new File(inputDirectory, "alternatives.xml"), true,
                executionResult, "alternatives");
        loadXMCDA(xmcda_v2, new File(inputDirectory, "alternativesMatrix.xml"), true,
                executionResult, "alternativesMatrix");
        loadXMCDA(xmcda_v2, new File(inputDirectory, "alternativesValues.xml"), false,
                executionResult, "alternativesValues");
        loadXMCDA(xmcda_v2, new File(inputDirectory, "parameters.xml"), true,
                executionResult, "programParameters");

        XMCDA xmcda = new XMCDA();


        /* Convert that to XMCDA v3 */
        try {
            xmcda = XMCDAConverter.convertTo_v3(xmcda_v2);
            for (Object o : xmcda_v2.getProjectReferenceOrMethodMessagesOrMethodParameters()) {
                JAXBElement<?> element = (JAXBElement) o;
                if (element.getValue() instanceof org.xmcda.v2.AlternativesMatrix) {
                    convertTo_v3((org.xmcda.v2.AlternativesMatrix) element.getValue(), xmcda);
                }
            }

        } catch (Throwable t) {
            executionResult.addError(getMessage("Could not convert inputs to XMCDA v3, reason: ", t));
        }

        return xmcda;
    }

    private void loadXMCDA(org.xmcda.v2.XMCDA xmcda_v2, File file, boolean mandatory, ProgramExecutionResult executionResults, String alternatives) {
        final String baseFilename = file.getName();

        if (!file.exists()) {
            if (mandatory) {
                executionResults.addError("Could not find the mandatory file " + baseFilename);
                return;
            } else
                return;
        }

        try {
            xmcda_v2.getProjectReferenceOrMethodMessagesOrMethodParameters()
                    .addAll(XMCDAParser.readXMCDA(file).getProjectReferenceOrMethodMessagesOrMethodParameters());

        } catch (Throwable throwable) {
            final String msg = String.format("Unable to read & parse the file %s, reason: ", baseFilename);
            executionResults.addError(XMCDAMessageParser.getMessage(msg, throwable));
        }
    }

    //custom converter for alternatives matrix
    private void convertTo_v3(org.xmcda.v2.AlternativesMatrix value, XMCDA xmcda_v3) {
        org.xmcda.AlternativesMatrix alternativesMatrix_v3 = Factory.alternativesMatrix();
        alternativesMatrix_v3.setId(value.getId());
        alternativesMatrix_v3.setMcdaConcept(value.getMcdaConcept());
        alternativesMatrix_v3.setName(value.getName());
        alternativesMatrix_v3.setDescription((new DescriptionConverter()).convertTo_v3(value.getDescription()));
        if (value.getValuation() != null) {
            alternativesMatrix_v3.setValuation((new ScaleConverter()).convertTo_v3(value.getValuation(), xmcda_v3));
        }

        Iterator var4 = value.getRow().iterator();

        while (var4.hasNext()) {
            org.xmcda.v2.AlternativesMatrix.Row row_v2 = (org.xmcda.v2.AlternativesMatrix.Row) var4.next();
            String row_alternativeID = row_v2.getAlternativeID();
            String row_alternativeSetID = row_v2.getAlternativesSetID();
            org.xmcda.v2.AlternativesSet row_alternativeSet_v2 = row_v2.getAlternativesSet();


            Alternative row_alternative = xmcda_v3.alternatives.get(row_alternativeID, true);
            Iterator var10 = row_v2.getColumn().iterator();

            while (var10.hasNext()) {
                org.xmcda.v2.AlternativesMatrix.Row.Column column_v2 = (org.xmcda.v2.AlternativesMatrix.Row.Column) var10.next();
                String column_alternativeID = column_v2.getAlternativeID();
                String column_alternativeSetID = column_v2.getAlternativesSetID();
                org.xmcda.v2.AlternativesSet column_alternativeSet_v2 = row_v2.getAlternativesSet();

                Alternative column_alternative = xmcda_v3.alternatives.get(column_alternativeID, true);
                QualifiedValue value_v3;
                if (column_v2.getValue() == null) {
                    value_v3 = Factory.qualifiedValue();
                    value_v3.setValue(NA.na);
                } else {
                    value_v3 = (new QualifiedValueConverter()).convertTo_v3(column_v2.getValue(), xmcda_v3);
                }

                Coord<Alternative, Alternative> coord_v3 = new Coord(row_alternative, column_alternative);
                QualifiedValues values_v3 = Factory.qualifiedValues();
                values_v3.add(value_v3);
                alternativesMatrix_v3.put(coord_v3, values_v3);
            }
        }

        xmcda_v3.alternativesMatricesList.add(alternativesMatrix_v3);
    }

//    public static void loadXMCDAv2(org.xmcda.v2.XMCDA xmcda_v2, File file, boolean mandatory,
//                                   ProgramExecutionResult x_execution_results, String ... load_tags)
//    {
//        final String baseFilename = file.getName();
//        if ( ! file.exists())
//        {
//            if ( mandatory )
//                x_execution_results.addError("Could not find the mandatory file " + baseFilename);
//            return;
//        }
//        try
//        {
//            readXMCDAv2_and_update(xmcda_v2, file, load_tags);
//        }
//        catch (Throwable throwable)
//        {
//            final String msg = String.format("Unable to read & parse the file %s, reason: ", baseFilename);
//            x_execution_results.addError(getMessage(msg, throwable));
//        }
//    }
//
//    public static void readXMCDAv2_and_update(org.xmcda.v2.XMCDA xmcda_v2, File file, String[] load_tags)
//            throws IOException, JAXBException, SAXException
//    {
//        final org.xmcda.v2.XMCDA new_xmcda = XMCDAParser.readXMCDA(file, load_tags);
//        final List<JAXBElement<?>> new_content = new_xmcda.getProjectReferenceOrMethodMessagesOrMethodParameters();
//        xmcda_v2.getProjectReferenceOrMethodMessagesOrMethodParameters().addAll(new_content);
//    }


}
