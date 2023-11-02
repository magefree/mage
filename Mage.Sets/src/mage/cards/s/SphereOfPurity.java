
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class SphereOfPurity extends CardImpl {

    public SphereOfPurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // If an artifact would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SphereOfPurityEffect()));
    }

    private SphereOfPurity(final SphereOfPurity card) {
        super(card);
    }

    @Override
    public SphereOfPurity copy() {
        return new SphereOfPurity(this);
    }
}

class SphereOfPurityEffect extends PreventionEffectImpl {

    public SphereOfPurityEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If an artifact would deal damage to you, prevent 1 of that damage";
    }

    private SphereOfPurityEffect(final SphereOfPurityEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && permanent.isArtifact(game)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public SphereOfPurityEffect copy() {
        return new SphereOfPurityEffect(this);
    }
}
