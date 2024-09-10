package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class RemoveCountersEvent extends GameEvent {

    boolean isDamage;

    public RemoveCountersEvent(String name, Card targetCard, Ability source, int amount, boolean isDamage){
        super(EventType.REMOVE_COUNTERS, targetCard.getId(), source,
                targetCard.getControllerOrOwnerId(), amount, false);

        if (source != null && source.getControllerId() != null) {
            setPlayerId(source.getControllerId()); // player who controls the source ability that removed the counters
        }
        setData(name);
        this.isDamage = isDamage;
    }

    public RemoveCountersEvent(String name, Player targetPlayer, Ability source, int amount, boolean isDamage){
        super(EventType.REMOVE_COUNTERS, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()), amount, false);
        setData(name);
        this.isDamage = isDamage;
    }

    public boolean counterRemovedDueToDamage(){
        return this.isDamage;
    }

}
