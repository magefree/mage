package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class RemoveCounterEvent extends GameEvent {

    public RemoveCounterEvent(String name, Card targetCard, Ability source, boolean isDamage){
        super(GameEvent.EventType.REMOVE_COUNTER, targetCard.getId(), source,
                targetCard.getControllerOrOwnerId(), 1, isDamage);

        if (source != null && source.getControllerId() != null) {
            setPlayerId(source.getControllerId()); // player who controls the source ability that removed the counters
        }
        setData(name);
    }

    public RemoveCounterEvent(String name, Player targetPlayer, Ability source, boolean isDamage){
        super(GameEvent.EventType.REMOVE_COUNTER, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()), 1, isDamage);
        setData(name);
    }

}
