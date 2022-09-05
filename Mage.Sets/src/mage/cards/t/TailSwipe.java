package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class TailSwipe extends CardImpl {

    public TailSwipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose target creature you control and target creature you don't control.
        // If you cast this spell during your main phase, the creature you control gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(1, 1)),
                AddendumCondition.instance,
                "Choose target creature you control and target creature you don't control. " +
                "If you cast this spell during your main phase, the creature you control gets +1/+1 until end of turn."
        ));

        // Then those creatures fight each other.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then those creatures fight each other. <i>(Each deals damage equal to its power to the other.)</i>")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private TailSwipe(final TailSwipe card) {
        super(card);
    }

    @Override
    public TailSwipe copy() {
        return new TailSwipe(this);
    }
}
