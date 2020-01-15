import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import discriminatorbug.FirstConcreteClass;
import discriminatorbug.SecondConcreteClass;
import discriminatorbug.TheAbstractClass;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DiscriminatorBugTestSuite {

  private FirstConcreteClass fcc;
  private SecondConcreteClass scc;

  @Test
  public void AddandFindTest() {

   //Initializing connection
    MongoWrapper mw = new MongoWrapper();
    // Dropping database
    mw.dropDatabase();
    var collection = mw.getCollection();

    // creating pojos to be insterted to the databases
    fcc = new FirstConcreteClass();
    fcc.setId(new ObjectId());
    fcc.setParameter("fcc object");
    fcc.setFirstClassLeafParameter("Leaf Param");

    scc = new SecondConcreteClass();
    scc.setId(new ObjectId());
    scc.setParameter("scc object");
    scc.setSecondClassParameter("leaf param again");



    collection.insertOne(fcc);
    collection.insertOne(scc);

    var shouldBeFcc = collection.find(Filters.eq(fcc.getId())).first();
    Assertions.assertEquals("fcc object", shouldBeFcc.getParameter());
    Assertions.assertEquals(fcc.getClass(), shouldBeFcc.getClass());

    var shouldBeScc = collection.find(Filters.eq(scc.getId())).first();
    Assertions.assertEquals("scc object", shouldBeScc.getParameter());
    Assertions.assertEquals(scc.getClass(), shouldBeScc.getClass());

    // We are closing the mongo client
    mw.closeClient();
    }

  @Test
  public void FindTestAfterReconnect() {

    // Making a new initialization of the client simulating an application redeployment or a restart

    var mw = new MongoWrapper();
    // Not dropping db, trying to fetch the documents previously added
    var collection2 = mw.getCollection();

    // Running the exact find query again
    var shouldBeFcc1 = collection2.find(Filters.eq(fcc.getId())).first();
    Assertions.assertEquals("fcc object", shouldBeFcc1.getParameter());
    Assertions.assertEquals(fcc.getClass(), shouldBeFcc1.getClass());

    var shouldBeScc1 = collection2.find(Filters.eq(scc.getId())).first();
    Assertions.assertEquals("scc object", shouldBeScc1.getParameter());
    Assertions.assertEquals(scc.getClass(), shouldBeScc1.getClass());

    mw.closeClient();

  }
}
