package Commands.ComparingDateTime;

import java.sql.Timestamp;

public class ScheduledEventImpl implements ScheduledEvent {
    private long nameID;
    private String name;
    private Timestamp timestamp;
    private String description;
    private long serverID;

    public ScheduledEventImpl() {
    }

    @Override
    public long getNameID() {
        return nameID;
    }

    @Override
    public void setNameID(long nameID) {
        this.nameID = nameID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Timestamp localDateTime) {
        this.timestamp = localDateTime;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public long getServerID() {
        return serverID;
    }

    @Override
    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

}