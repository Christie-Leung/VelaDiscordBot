package Commands.General;

public interface GuildsSettings {

    int getGuildMembers();

    long getGuildId();

    String getGuildName();

    String getGuildPrefix();

    String getGuildWelcomeMsg();

    void setGuildId(long guildId);

    void setGuildMembers(int guildMembers);

    void setGuildName(String guildName);

    void setGuildPrefix(String guildPrefix);

    void setGuildWelcomeMsg(String guildWelcomeMsg);
}
