
package mage.cards.s;

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
 * @author spjspj
 */
public final class SquirrelPoweredScheme extends CardImpl {

    public SquirrelPoweredScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Increase the result of each die you roll by 2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SquirrelPoweredSchemeEffect()));
    }

    public SquirrelPoweredScheme(final SquirrelPoweredScheme card) {
        super(card);
    }

    @Override
    public SquirrelPoweredScheme copy() {
        return new SquirrelPoweredScheme(this);
    }
}

class SquirrelPoweredSchemeEffect extends ReplacementEffectImpl {

    SquirrelPoweredSchemeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Increase the result of each die you roll by 2";
    }

    SquirrelPoweredSchemeEffect(final SquirrelPoweredSchemeEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // ignore planar dies (dice roll amount of planar dies is equal to 0)
        return event.getAmount() > 0 && source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SquirrelPoweredSchemeEffect copy() {
        return new SquirrelPoweredSchemeEffect(this);
    }
}
