package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {



   public static Courier getRandom(){
       final String login = RandomStringUtils.randomAlphabetic(11);
       final String password = RandomStringUtils.randomAlphabetic(11);
       final String firstName = RandomStringUtils.randomAlphabetic(11);
       return new Courier(login, password, firstName);
   }
}
