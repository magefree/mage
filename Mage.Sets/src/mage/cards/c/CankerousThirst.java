
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class CankerousThirst extends CardImpl {

    public CankerousThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B/G}");

        // If {B} was spent to cast Cankerous Thirst, you may have target creature get -3/-3 until end of turn. If {G} was spent to cast Cankerous Thirst, you may have target creature get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(-3, -3, Duration.EndOfTurn),
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.B)),
                "If {B} was spent to cast {this}, you may have target creature get -3/-3 until end of turn"));

        ContinuousEffect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                effect,
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.G)),
                "If {G} was spent to cast {this}, you may have target creature get +3/+3 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {B}{G} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    public CankerousThirst(final CankerousThirst card) {
        super(card);
    }

    @Override
    public CankerousThirst copy() {
        return new CankerousThirst(this);
    }

}
