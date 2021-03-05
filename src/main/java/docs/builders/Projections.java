package docs.builders;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;
// begin static import
import static com.mongodb.client.model.Projections.*;
// end static import

public class Projections {
    private final MongoCollection<Document> collection;
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private Projections() {
        final String uri = System.getenv("DRIVER_REF_SRV");

        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("builders");
        collection = database.getCollection("projections");
    }

    public static void main(String[] args) {
        Projections projections = new Projections();
        projections.setupCollection();
        projections.includeOneField();
        projections.includeMultipleFields();
        projections.excludeOneField();
        projections.excludeMultipleFields();
        projections.showFields();
        projections.excludeConvenienceId();

    }

    private void excludeConvenienceId() {
        // begin exclude id
        Bson filter = new Document();
        Bson projection = fields(include("year", "type"), excludeId());
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end exclude id
    }

    private void showFields() {
        // begin show fields
        Bson filter = new Document();
        Bson projection = fields(include("year", "type"), exclude("_id"));
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end show fields
    }



    private void excludeMultipleFields() {
        // begin exclude multiple fields
        Bson filter = new Document();
        Bson projection = exclude("temperatures", "type");
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end exclude multiple fields
    }

    private void excludeOneField() {
        // begin exclude one field
        Bson filter = new Document();
        Bson projection = exclude("temperatures");
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end exclude one field
    }

    private void includeMultipleFields() {
        // begin include multiple fields
        Bson filter = new Document();
        Bson projection = include("year", "type");
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));

        // end include multiple fields
    }

    private void includeOneField() {
        // begin include single field
        Bson filter = new Document();
        Bson projection = include("year");
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end include single field
    }

    private void setupCollection() {
        // The global average temperature, by month, from 2018 and 2019. Units are in
        // Celsius.
        List<Document> demoDocuments = Arrays.asList(
                new Document("year", 2018).append("type", "common").append("temperatures",
                        Arrays.asList(new Document("January", 9.765), new Document("February", 9.675),
                                new Document("March", 10.004), new Document("April", 9.983), new Document("May", 9.747),
                                new Document("June", 9.65), new Document("July", 9.786), new Document("August", 9.617),
                                new Document("September", 9.51), new Document("October", 10.042),
                                new Document("November", 9.452), new Document("December", 9.86))),
                new Document("year", 2019).append("type", "common").append("temperatures",
                        Arrays.asList(new Document("January", 10.023), new Document("February", 9.808),
                                new Document("March", 10.43), new Document("April", 10.175), new Document("May", 9.648),
                                new Document("June", 9.686), new Document("July", 9.794), new Document("August", 9.741),
                                new Document("September", 9.84), new Document("October", 10.15),
                                new Document("November", 9.84), new Document("December", 10.366))));

        collection.drop();
        collection.insertMany(demoDocuments);
    }

}
