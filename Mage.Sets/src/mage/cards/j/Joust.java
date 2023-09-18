package mage.cards.j;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.TargetHasSubtypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class Joust extends CardImpl {

    private static final Condition condition = new TargetHasSubtypeCondition(SubType.KNIGHT);

    public Joust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Choose target creature you control and target creature you don't control.
        // The creature you control gets +2/+1 until end of turn if it's a Knight.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(2, 1)),
                condition,
                "Choose target creature you control and target creature you don't control. " +
                "The creature you control gets +2/+1 until end of turn if it's a Knight."
        ));

        // Then those creatures fight each other.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then those creatures fight each other. <i>(Each deals damage equal to its power to the other.)</i>")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private Joust(final Joust card) {
        super(card);
    }

    @Override
    public Joust copy() {
        return new Joust(this);
    }
}
