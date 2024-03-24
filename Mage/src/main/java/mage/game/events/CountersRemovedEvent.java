package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

public class CountersRemovedEvent extends GameEvent {

    public CountersRemovedEvent(String name, Card targetCard, Ability source, int amount, boolean isDamage){
        super(EventType.COUNTERS_REMOVED, targetCard.getId(), source,
                targetCard.getControllerOrOwnerId(), amount, isDamage);

        if (source != null && source.getControllerId() != null) {
            setPlayerId(source.getControllerId()); // player who controls the source ability that removed the counters
        }
        setData(name);
    }

    public CountersRemovedEvent(String name, Player targetPlayer, Ability source, int amount, boolean isDamage){
        super(EventType.COUNTERS_REMOVED, targetPlayer.getId(), source,
                (source == null ? null : source.getControllerId()), amount, isDamage);
        setData(name);
    }

}
