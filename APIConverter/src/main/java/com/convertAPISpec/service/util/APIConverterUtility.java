package com.convertAPISpec.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.wso2.soaptorest.SOAPToRESTConverter;
import org.wso2.soaptorest.models.SOAPtoRESTConversionData;

import com.convertAPISpec.service.APIConverterDTO;
import com.convertAPISpec.service.APIConverterService;

@Service
public class APIConverterUtility implements APIConverterService {
	private static String converter(String inputFile, String format) {
		String converterCommand = "cmd /c api-spec-converter --from=" + format + " --to=openapi_3 "
				+ " --syntax=yaml --order=alpha " + inputFile;
		return converterCommand;
	}

	private static void writeConvertedFile(String inputFile, FileWriter converterWriter, String format)
			throws Exception {

		if (format.equalsIgnoreCase("wsdl")) {
			String data = inputFile;
			File inFile = new File(inputFile);
			if(inFile.exists()) {
				data = inFile.getAbsolutePath();
			}
			SOAPtoRESTConversionData soapRequestBodyMapping = SOAPToRESTConverter.getSOAPtoRESTConversionData(data,
					"", "");
			converterWriter.write(soapRequestBodyMapping.getOASString());
			converterWriter.close();
		} else {
			Process convertSOAPToOpenAPI = Runtime.getRuntime().exec(converter(inputFile, format));

			BufferedReader convertSOAPToOpenAPIReader = new BufferedReader(convertSOAPToOpenAPI.inputReader());
			String converterString;
			while ((converterString = convertSOAPToOpenAPIReader.readLine()) != null) {
				converterWriter.write(converterString + "\n");
			}
			convertSOAPToOpenAPIReader.close();
			convertSOAPToOpenAPI.destroy();
			converterWriter.close();
		}

	}

	@Override
	public APIConverterDTO runConverter(APIConverterDTO apiConverterDTO) {

		String inputFile = apiConverterDTO.getInputFile();
		FileWriter outputFile = null;
		try {
			outputFile = new FileWriter(apiConverterDTO.getOutFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fromFormat = apiConverterDTO.getFromFormat();
		try {
			writeConvertedFile(inputFile, outputFile, fromFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return apiConverterDTO;
	}

}
