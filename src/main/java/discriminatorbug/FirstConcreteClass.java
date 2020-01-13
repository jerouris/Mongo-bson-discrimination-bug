package discriminatorbug;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(value = "FirstConcreteClass")
public class FirstConcreteClass extends TheAbstractClass {

  private String firstClassLeafParameter;

  public String getFirstClassLeafParameter() {
    return firstClassLeafParameter;
  }

  public void setFirstClassLeafParameter(String firstClassLeafParameter) {
    this.firstClassLeafParameter = firstClassLeafParameter;
  }
}
