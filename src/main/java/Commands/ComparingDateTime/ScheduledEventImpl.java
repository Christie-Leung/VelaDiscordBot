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

    public ScheduledEventImpl(String[] args) {
        if(args.length != 5) return;
        setNameID(Long.parseLong(args[0]));
        setName(args[1]);
        setTimestamp(Timestamp.valueOf(args[2]));
        setDescription(args[3]);
        setServerID(Long.parseLong(args[4]));
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