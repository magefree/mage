
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public final class DivinePresence extends CardImpl {

    public DivinePresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // If a source would deal 4 or more damage to a creature or player, that source deals 3 damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DivinePresenceEffect()));
    }

    private DivinePresence(final DivinePresence card) {
        super(card);
    }

    @Override
    public DivinePresence copy() {
        return new DivinePresence(this);
    }
}

class DivinePresenceEffect extends ReplacementEffectImpl {

    public DivinePresenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a source would deal 4 or more damage to a permanent or player, that source deals 3 damage to that permanent or player instead.";
    }

    private DivinePresenceEffect(final DivinePresenceEffect effect) {
        super(effect);
    }

    @Override
    public DivinePresenceEffect copy() {
        return new DivinePresenceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getAmount() > 3;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(3);
        return false;
    }
}
