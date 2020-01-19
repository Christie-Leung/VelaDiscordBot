package Commands.General;

public class GuildsSettingsImpl implements GuildsSettings {
    long guildId;
    String guildName;
    String guildPrefix;
    String guildWelcomeMsg;
    int guildMembers;

    public GuildsSettingsImpl() {
    }

    public int getGuildMembers() {
        return guildMembers;
    }

    public long getGuildId() {
        return guildId;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getGuildPrefix() {
        return guildPrefix;
    }

    public String getGuildWelcomeMsg() {
        return guildWelcomeMsg;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setGuildMembers(int guildMembers) {
        this.guildMembers = guildMembers;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void setGuildPrefix(String guildPrefix) {
        this.guildPrefix = guildPrefix;
    }

    public void setGuildWelcomeMsg(String guildWelcomeMsg) {
        this.guildWelcomeMsg = guildWelcomeMsg;
    }
}
