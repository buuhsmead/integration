package integration.camel.csv2soap;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;

public class Csv2SoapRoute extends RouteBuilder {

	private static final String FILENAME_REGEX = "^[^-]*-[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]";
	private static final String CAMEL_VELOCITY_RESOURCE_URI = "CamelVelocityResourceUri";
	private static final String FILENAME = "filename";
	private static final String DATATYPE = "datatype";
	private static final String TIMESTAMP = "timestamp";
	private static final String VALUES = "values";
	private static final String URL = "URL";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String LINE = "line";
	private static final String REQUEST_MESSAGE = "RequestMessage";
	private static final String RESPONSE_MESSAGE = "ResponseMessage";

	@Override
	public void configure() throws Exception {
		CsvDataFormat csv = new CsvDataFormat();
		from("file:{{import.dir}}/?include=.*.CSV|.*.csv")

				.setProperty(
						FILENAME,
						regexReplaceAll(simple("${file:name}"), "\\..*",
								constant("")))
				.choice()
				.when(property(FILENAME).not().regex(FILENAME_REGEX))
				.throwException(
						new RuntimeException(
								"Filename should have pattern like: TABLE-2002-05-30T09:00:00.CSV"))
				.otherwise()
				.setProperty(
						DATATYPE,
						regexReplaceAll(simple("${property." + FILENAME + "}"),
								"-.*", constant("")))
				.setProperty(
						TIMESTAMP,
						regexReplaceAll(simple("${property." + FILENAME + "}"),
								"^[^-]*-", constant("")))
				.log("FILENAME: ${header." + FILENAME + "}"
						+ ", DATATYPE: ${header." + DATATYPE + "}"
						+ ", TIMESTAMP: ${header." + TIMESTAMP + "}")
				.split(body().tokenize("\n"))
				.streaming()
				.to("direct:process")
				.choice()
				.when(property(RESPONSE_MESSAGE).not().contains("soap:Body"))
				.setHeader("CamelFileName",
						simple("${property." + FILENAME + "}.success"))
				.transform(simple("${property." + LINE + "}\\n"))
				.to("file:{{output.dir}}?fileExist=Append")
				.otherwise()
				.setHeader("CamelFileName",
						simple("${property." + FILENAME + "}.fail"))
				.transform(simple("${property." + LINE + "}\\n"))
				.to("file:{{output.dir}}?fileExist=Append");


		from("direct:process")
				.setProperty(LINE, simple("${body}"))
				.unmarshal(csv)
				.transform(simple("${body[0]}"))
				.setHeader(USER, simple("${properties:user}"))
				.setHeader(PASSWORD, simple("${properties:password}"))
				.setHeader(TIMESTAMP, simple("${property." + TIMESTAMP + "}"))
				.setHeader(VALUES, simple("${body}"))
				.setHeader(DATATYPE, simple("${property." + DATATYPE + "}"))
				.setHeader(CAMEL_VELOCITY_RESOURCE_URI)
				.simple("file:{{template.dir}}/${property." + DATATYPE
						+ "}.xml")
				.to("velocity:dummy")
				.setProperty(REQUEST_MESSAGE, simple("${body}"))
				.setProperty(
						URL,
						simple("${properties:url}/${property." + DATATYPE + "}"))
				.log("${property." + URL + "}")
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.log("HTTP_METHOD: ${headers." + Exchange.HTTP_METHOD + "}")
				.log("${body}")
				.recipientList(simple("${property." + URL + "}"))
				.log("${body}").setProperty(RESPONSE_MESSAGE, simple("${body}"));
		
	}
}
