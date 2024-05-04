package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class RemoveCounterEvent extends GameEvent {

    boolean isDamage;

    public RemoveCounterEvent(String name, Card targetCard, Ability source, boolean isDamage){
        super(GameEvent.EventType.REMOVE_COUNTER, targetCard.getId(), source,
                (source == null ? null : source.getControllerId()));
        setData(name);
        this.isDamage = isDamage;
    }

    public RemoveCounterEvent(String name, Player targetPlayer, Ability source, boolean isDamage){
        super(GameEvent.EventType.REMOVE_COUNTER, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()));
        setData(name);
        this.isDamage = isDamage;
    }

    boolean counterRemovedDueToDamage(){
        return this.isDamage;
    }

}
