package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessStormseeker extends TransformingDoubleFacedCard {

    public RecklessStormseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}",
                "Storm-Charged Slasher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Reckless Stormseeker
        this.getLeftHalfCard().setPT(2, 3);

        // At the beginning of combat on your turn, target creature you control gets +1/+0 and gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(1, 0)
                        .setText("target creature you control gets +1/+0")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Storm-Charged Slasher
        this.getRightHalfCard().setPT(3, 4);

        // At the beginning of combat on your turn, target creature you control gets +2/+0 and gains trample and haste until end of turn.
        Ability ability2 = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0)
                        .setText("target creature you control gets +2/+0")
        );
        ability2.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample"));
        ability2.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));
        ability2.addTarget(new TargetControlledCreaturePermanent());
        this.getRightHalfCard().addAbility(ability2);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private RecklessStormseeker(final RecklessStormseeker card) {
        super(card);
    }

    @Override
    public RecklessStormseeker copy() {
        return new RecklessStormseeker(this);
    }
}
