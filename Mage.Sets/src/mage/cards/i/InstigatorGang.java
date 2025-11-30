package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class InstigatorGang extends TransformingDoubleFacedCard {

    public InstigatorGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}",
                "Wildblood Pack",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Instigator Gang
        this.getLeftHalfCard().setPT(2, 3);

        // Attacking creatures you control get +1/+0.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        )));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Instigator Gang.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Wildblood Pack
        this.getRightHalfCard().setPT(5, 5);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Attacking creatures you control get +3/+0.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                3, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        )));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Wildblood Pack.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private InstigatorGang(final InstigatorGang card) {
        super(card);
    }

    @Override
    public InstigatorGang copy() {
        return new InstigatorGang(this);
    }
}
