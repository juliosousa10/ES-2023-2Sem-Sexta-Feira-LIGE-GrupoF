package GrupoF.project;

import java.io.File;


//<dependency>
//  <groupId>com.fasterxml.jackson.dataformat</groupId>
//  <artifactId>jackson-dataformat-csv</artifactId>
//  <version>2.13.0</version>
//</dependency>

//<dependency>
//  <groupId>com.fasterxml.jackson.core</groupId>
//  <artifactId>jackson-databind</artifactId>
//  <version>2.13.0</version>
//</dependency>

JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/orderLines.json"));

Builder csvSchemaBuilder = CsvSchema.builder();
JsonNode firstObject = jsonTree.elements().next();
firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

CsvMapper csvMapper = new CsvMapper();
csvMapper.writerFor(JsonNode.class)
.with(csvSchema)
.writeValue(new File("src/main/resources/orderLines.csv"), jsonTree);

//read CSV and Write JSON

public class OrderLine {
  private String item;
  private int quantity;
  private BigDecimal unitPrice;

  // Constructors, Getters, Setters and toString
}

CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
CsvMapper csvMapper = new CsvMapper();
MappingIterator<OrderLine> orderLines = csvMapper.readerFor(OrderLine.class)
.with(orderLineSchema)
.readValues(new File("src/main/resources/orderLines.csv"));

new ObjectMapper()
.configure(SerializationFeature.INDENT_OUTPUT, true)
.writeValue(new File("src/main/resources/orderLinesFromCsv.json"), orderLines.readAll());

//configuring the CSV File Format

@JsonPropertyOrder({
	  "count",
	  "name"
	})


public abstract class OrderLineForCsv {
  
  @JsonProperty("name")
  private String item; 
  
  @JsonProperty("count")
  private int quantity; 
  
  @JsonIgnore
  private BigDecimal unitPrice;

}

CsvMapper csvMapper = new CsvMapper();
CsvSchema csvSchema = csvMapper
.schemaFor(OrderLineForCsv.class)
.withHeader(); 

csvMapper.addMixIn(OrderLine.class, OrderLineForCsv.class); 

OrderLine[] orderLines = new ObjectMapper()
  .readValue(new File("src/main/resources/orderLines.json"), OrderLine[].class);
  
csvMapper.writerFor(OrderLine[].class)
  .with(csvSchema)
  .writeValue(new File("src/main/resources/orderLinesReformated.csv"), orderLines);

