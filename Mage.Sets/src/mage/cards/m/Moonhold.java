
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class Moonhold extends CardImpl {

    public Moonhold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R/W}");

        // Target player can't play land cards this turn if {R} was spent to cast Moonhold and can't play creature cards this turn if {W} was spent to cast it.
        ContinuousRuleModifyingEffect effect = new MoonholdEffect();
        ContinuousRuleModifyingEffect effect2 = new MoonholdEffect2();
        effect.setText("Target player can't play lands this turn if {R} was spent to cast this spell");
        effect2.setText("and can't cast creature spells this turn if {W} was spent to cast this spell.");
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                effect,
                new LockedInCondition(ManaWasSpentCondition.RED)));
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                effect2,
                new LockedInCondition(ManaWasSpentCondition.WHITE)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new InfoEffect(" <i>(Do both if {R}{W} was spent.)</i>"));
    }

    private Moonhold(final Moonhold card) {
        super(card);
    }

    @Override
    public Moonhold copy() {
        return new Moonhold(this);
    }
}

class MoonholdEffect extends ContinuousRuleModifyingEffectImpl {

    public MoonholdEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    public MoonholdEffect(final MoonholdEffect effect) {
        super(effect);
    }

    @Override
    public MoonholdEffect copy() {
        return new MoonholdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "you can't play land cards this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }
}

class MoonholdEffect2 extends ContinuousRuleModifyingEffectImpl {

    public MoonholdEffect2() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    public MoonholdEffect2(final MoonholdEffect2 effect) {
        super(effect);
    }

    @Override
    public MoonholdEffect2 copy() {
        return new MoonholdEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't play creature cards this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}
