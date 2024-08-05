package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class CountersRemovedEvent extends GameEvent {

    boolean isDamage;

    public CountersRemovedEvent(String name, Card targetCard, Ability source, int amount, boolean isDamage){
        super(EventType.COUNTERS_REMOVED, targetCard.getId(), source,
                (source == null ? null : source.getControllerId()), amount, false);
        setData(name);
        this.isDamage = isDamage;
    }

    public CountersRemovedEvent(String name, Player targetPlayer, Ability source, int amount, boolean isDamage){
        super(EventType.COUNTERS_REMOVED, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()), amount, false);
        setData(name);
        this.isDamage = isDamage;
    }

    boolean counterRemovedDueToDamage(){
        return this.isDamage;
    }

}
