package Commands.ComparingDateTime.Timezones;

import java.sql.Timestamp;

public class UserTimezoneImpl implements UserTimezone {

    private String user;
    private Timestamp timezone;

    public UserTimezoneImpl(String[] args) {
        if(args.length != 3) return;
        setUser(args[0]);
        setTimezone(Timestamp.valueOf(args[1]));
    }

    public UserTimezoneImpl() {

    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Timestamp getTimezone() {
        return timezone;
    }

    @Override
    public void setTimezone(Timestamp timezone) {
        this.timezone = timezone;
    }
}
