package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDieEvent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class SquirrelPoweredScheme extends CardImpl {

    public SquirrelPoweredScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Increase the result of each die you roll by 2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SquirrelPoweredSchemeEffect()));
    }

    private SquirrelPoweredScheme(final SquirrelPoweredScheme card) {
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
        ((RollDieEvent) event).incResultModifier(2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DIE
                && ((RollDieEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
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
