package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormKingsThunder extends CardImpl {

    public StormKingsThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}{R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell X times. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new StormKingsThunderEffect());
    }

    private StormKingsThunder(final StormKingsThunder card) {
        super(card);
    }

    @Override
    public StormKingsThunder copy() {
        return new StormKingsThunder(this);
    }
}

class StormKingsThunderEffect extends OneShotEffect {

    StormKingsThunderEffect() {
        super(Outcome.Benefit);
        staticText = "When you cast your next instant or sorcery spell this turn, " +
                "copy that spell X times. You may choose new targets for the copies.";
    }

    private StormKingsThunderEffect(final StormKingsThunderEffect effect) {
        super(effect);
    }

    @Override
    public StormKingsThunderEffect copy() {
        return new StormKingsThunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new StormKingsThunderAbility(source.getManaCostsToPay().getX()), source);
        return true;
    }
}

class StormKingsThunderAbility extends DelayedTriggeredAbility {

    StormKingsThunderAbility(int xValue) {
        super(new StormKingsThunderCopyEffect(xValue), Duration.EndOfTurn);
    }

    private StormKingsThunderAbility(final StormKingsThunderAbility ability) {
        super(ability);
    }

    @Override
    public StormKingsThunderAbility copy() {
        return new StormKingsThunderAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, " +
                "copy that spell X times. You may choose new targets for the copies.";
    }

}

class StormKingsThunderCopyEffect extends OneShotEffect {

    private final int xValue;

    StormKingsThunderCopyEffect(int xValue) {
        super(Outcome.Benefit);
        this.xValue = xValue;
    }

    private StormKingsThunderCopyEffect(final StormKingsThunderCopyEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public StormKingsThunderCopyEffect copy() {
        return new StormKingsThunderCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (xValue < 1) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, xValue);
        return true;
    }
}
