
package mage.cards.p;

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

/**
 *
 * @author fireshoes
 */
public final class ProtectionOfTheHekma extends CardImpl {

    public ProtectionOfTheHekma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");


        // If a source an opponent controls would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ProtectionOfTheHekmaEffect()));
    }

    private ProtectionOfTheHekma(final ProtectionOfTheHekma card) {
        super(card);
    }

    @Override
    public ProtectionOfTheHekma copy() {
        return new ProtectionOfTheHekma(this);
    }
}

class ProtectionOfTheHekmaEffect extends PreventionEffectImpl {

    public ProtectionOfTheHekmaEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source an opponent controls would deal damage to you, prevent 1 of that damage";
    }

    private ProtectionOfTheHekmaEffect(final ProtectionOfTheHekmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(source.getControllerId()).contains(sourceControllerId)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public ProtectionOfTheHekmaEffect copy() {
        return new ProtectionOfTheHekmaEffect(this);
    }
}
