package docs.aggregation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.Arrays;

public class AggTourTwo {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("restaurants");
        Document filter = new Document().append("gender", "female").append("age", new Document().append("$gt", 29));
        Document projection = new Document().append("_id", 0).append("email", 1);
        collection.find(filter).projection(projection);

        collection.aggregate(
            Arrays.asList(
                Aggregates.project(
                    Projections.fields(
                        Projections.excludeId(),
                        Projections.include("name"),
                        Projections.computed(
                            "firstCategory",
                            new Document("$arrayElemAt", Arrays.asList("$categories", 0))
                        )
                    )
                )
            )
        ).allowDiskUse(true).forEach(doc -> System.out.println(doc.toJson()));
    }

    private static int multiply(int[] l, int[] r) {
        return 0;
    }
}
