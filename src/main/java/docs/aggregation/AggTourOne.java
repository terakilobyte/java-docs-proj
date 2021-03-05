package docs.aggregation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;

public class AggTourOne {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("restaurants");
        collection.aggregate(
            Arrays.asList(
                Aggregates.match(Filters.eq("categories", "Bakery")),
                Aggregates.group("$stars", Accumulators.sum("count", 1))
            )
        ).forEach(doc -> System.out.println(doc.toJson()));
    }
}
