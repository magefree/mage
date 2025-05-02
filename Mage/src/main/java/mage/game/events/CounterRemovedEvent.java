package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class CounterRemovedEvent extends GameEvent {

    boolean isDamage;

    public CounterRemovedEvent(String name, Card targetCard, Ability source, boolean isDamage){
        super(EventType.COUNTER_REMOVED, targetCard.getId(), source,
                (source == null ? null : source.getControllerId()));
        setData(name);
        this.isDamage = isDamage;
    }

    public CounterRemovedEvent(String name, Player targetPlayer, Ability source, boolean isDamage){
        super(EventType.COUNTER_REMOVED, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()));
        setData(name);
        this.isDamage = isDamage;
    }

    public boolean counterRemovedDueToDamage(){
        return this.isDamage;
    }

}
