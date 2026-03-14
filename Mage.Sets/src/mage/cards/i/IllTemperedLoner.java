package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllTemperedLoner extends TransformingDoubleFacedCard {

    public IllTemperedLoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}{R}",
                "Howlpack Avenger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Ill-Tempered Loner
        this.getLeftHalfCard().setPT(3, 3);

        // Whenever Ill-Tempered Loner is dealt damage, it deals that much damage to any target.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DamageTargetEffect(SavedDamageValue.MUCH, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.getLeftHalfCard().addAbility(ability);

        // {1}{R}: Ill-Tempered Loner gets +2/+0 until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Howlpack Avenger
        this.getRightHalfCard().setPT(4, 4);

        // Whenever a permanent you control is dealt damage, Howlpack Avenger deals that much damage to any target.
        Ability backAbility = new DealtDamageAnyTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH),
                StaticFilters.FILTER_CONTROLLED_A_PERMANENT, SetTargetPointer.NONE, false);
        backAbility.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(backAbility);

        // {1}{R}: Howlpack Avenger gets +2/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private IllTemperedLoner(final IllTemperedLoner card) {
        super(card);
    }

    @Override
    public IllTemperedLoner copy() {
        return new IllTemperedLoner(this);
    }
}
