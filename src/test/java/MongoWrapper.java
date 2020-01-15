import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import discriminatorbug.TheAbstractClass;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoWrapper {
  public static final String DBURL = "mongodb://192.168.30.100:27017";
  public static final String DB = "bug-db";
  public static final String CLX = "bug-collection";

  public final MongoClient cl;
  public final CodecRegistry reg;
  public final MongoDatabase db;

  public MongoWrapper() {
  cl = MongoClients.create(DBURL);
  db = cl.getDatabase(DB);

  // Initialize the default Codec Registry
  reg = fromRegistries(com.mongodb.MongoClientSettings.getDefaultCodecRegistry(),
      fromProviders(PojoCodecProvider
          .builder()
          .automatic(true)
          .build()));

  // Initialize the collection. It uses the default codec registry and maps to TheAbstractClass
    }
   public MongoCollection<TheAbstractClass> getCollection() {
     return db.getCollection( CLX, TheAbstractClass.class)
         .withCodecRegistry(reg);
    }

    public void dropDatabase() {
      this.db.drop();
    }

    public void closeClient() {
    this.cl.close();
  }
}
