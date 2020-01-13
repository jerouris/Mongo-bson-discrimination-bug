package discriminatorbug;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(value = "SecondConcreteClass")
public class SecondConcreteClass extends TheAbstractClass {

  private String SecondClassParameter;

  public String getSecondClassParameter() {
    return SecondClassParameter;
  }

  public void setSecondClassParameter(String secondClassParameter) {
    SecondClassParameter = secondClassParameter;
  }
}
