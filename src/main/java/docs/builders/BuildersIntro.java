package docs.builders;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
// end static imports

public class BuildersIntro {
    private final MongoCollection<Document> collection;
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private BuildersIntro() {
        final String uri = System.getenv("DRIVER_REF_SRV");
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("builders");
        collection = database.getCollection("intro");
    }

    public static void main(String[] args) {
        BuildersIntro intro = new BuildersIntro();
        intro.noFilters();
        intro.filters();

    }

    private void collectionSetup() {
        collection.drop();
    }

    private void noFilters() {
        // begin long
        Bson filter = new Document().append("gender", "female").append("age", new Document().append("$gt", 29));
        Bson projection = new Document().append("_id", 0).append("email", 1);
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end long
    }

    private void filters() {
        // begin filters
        Bson filter = and(eq("gender", "female"), gt("age", 29));
        Bson projection = fields(excludeId(), include("email"));
        collection.find(filter).projection(projection).forEach(doc -> System.out.println(doc.toJson()));
        // end filters
    }
}
