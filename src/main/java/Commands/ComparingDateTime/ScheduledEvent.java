package Commands.ComparingDateTime;

import java.sql.Timestamp;

public interface ScheduledEvent {

    long getNameID();

    void setNameID(long nameID);

    String getName();

    void setName(String name);

    Timestamp getTimestamp();

    void setTimestamp(Timestamp localDateTime);

    String getDescription();

    void setDescription(String description);

    long getServerID();

    void setServerID(long serverID);

}
