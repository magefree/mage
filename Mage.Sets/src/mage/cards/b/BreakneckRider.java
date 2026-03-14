package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BreakneckRider extends TransformingDoubleFacedCard {

    public BreakneckRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SCOUT, SubType.WEREWOLF}, "{1}{R}{R}",
                "Neck Breaker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R");

        // Breakneck Rider
        this.getLeftHalfCard().setPT(3, 3);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Breakneck Rider.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Neck Breaker
        this.getRightHalfCard().setPT(4, 3);

        // Attacking creatures you control get +1/+0 and have trample.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("and have trample"));
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Neck Breaker.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private BreakneckRider(final BreakneckRider card) {
        super(card);
    }

    @Override
    public BreakneckRider copy() {
        return new BreakneckRider(this);
    }
}
