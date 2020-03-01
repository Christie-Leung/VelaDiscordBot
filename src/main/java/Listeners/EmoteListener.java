package Listeners;

import net.dv8tion.jda.api.events.emote.GenericEmoteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class EmoteListener extends ListenerAdapter {

    @Override
    public void onGenericEmote(GenericEmoteEvent e) {
        e.getEmote().getRoles();
        doEmoteThing(e);
    }

    public abstract void doEmoteThing(GenericEmoteEvent e);
}
