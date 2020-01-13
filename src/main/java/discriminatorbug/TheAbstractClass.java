package discriminatorbug;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator
public abstract class TheAbstractClass {

  protected String aParameter;

  public String getaParameter() {
    return aParameter;
  }

  public void setaParameter(String aParameter) {
    this.aParameter = aParameter;
  }
}
