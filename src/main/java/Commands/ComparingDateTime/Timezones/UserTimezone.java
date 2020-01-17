package Commands.ComparingDateTime.Timezones;

import java.sql.Timestamp;

public interface UserTimezone {

    String getUser();

    void setUser(String name);

    Timestamp getTimezone();

    void setTimezone(Timestamp localDateTime);
}
