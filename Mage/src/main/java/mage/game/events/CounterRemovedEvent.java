package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class CounterRemovedEvent extends GameEvent {

    public CounterRemovedEvent(String name, Card targetCard, Ability source, boolean isDamage){
        super(EventType.COUNTER_REMOVED, targetCard.getId(), source,
                targetCard.getControllerOrOwnerId(), 1, isDamage);

        if (source != null && source.getControllerId() != null) {
            setPlayerId(source.getControllerId()); // player who controls the source ability that removed the counters
        }
        setData(name);
    }

    public CounterRemovedEvent(String name, Player targetPlayer, Ability source, boolean isDamage){
        super(EventType.COUNTER_REMOVED, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()), 1, isDamage);
        setData(name);
    }

}
