package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessStormseeker extends CardImpl {

    public RecklessStormseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.s.StormChargedSlasher.class;

        // At the beginning of combat on your turn, target creature you control gets +1/+0 and gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(1, 0)
                        .setText("target creature you control gets +1/+0"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private RecklessStormseeker(final RecklessStormseeker card) {
        super(card);
    }

    @Override
    public RecklessStormseeker copy() {
        return new RecklessStormseeker(this);
    }
}
