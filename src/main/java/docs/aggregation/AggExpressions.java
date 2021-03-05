package docs.aggregation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.fields;

public class AggExpressions {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("aggregation");
        MongoCollection<Document> collection = database.getCollection("expressions");

        // drop the collection
        collection.drop();

        // insert new shape documents
        collection.insertMany(Arrays.asList(
            new Document("shape", new Date())
        ));

        collection
            .aggregate(
                Arrays.asList(
                    project(fields(
                        computed("year", Document.parse("{$dateToString: {format: '%Y', date: '$shape'}}")),
                        computed("month", Document.parse("{$dateToString: {format: '%m', date: '$shape'}}")),
                        computed("day", Document.parse("{$dateToString: {format: '%d', date: '$shape'}}"))
                        )
                    )
                ))
            .forEach(doc -> System.out.println(doc.toJson()));
    }
}
