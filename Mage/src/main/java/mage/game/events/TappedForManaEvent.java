package mage.game.events;

import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class TappedForManaEvent extends ManaEvent {

    private final Permanent permanent;

    public TappedForManaEvent(UUID targetId, Ability source, UUID playerId, Mana mana, Game game) {
        super(EventType.TAPPED_FOR_MANA, targetId, source, playerId, mana);
        this.permanent = source.getSourcePermanentOrLKI(game);
    }

    public Permanent getPermanent() {
        return permanent;
    }
}
