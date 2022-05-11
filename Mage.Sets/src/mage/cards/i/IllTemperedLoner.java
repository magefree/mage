package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllTemperedLoner extends CardImpl {

    public IllTemperedLoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.h.HowlpackAvenger.class;

        // Whenever Ill-Tempered Loner is dealt damage, it deals that much damage to any target.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DamageTargetEffect(SavedDamageValue.MUCH, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {1}{R}: Ill-Tempered Loner gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private IllTemperedLoner(final IllTemperedLoner card) {
        super(card);
    }

    @Override
    public IllTemperedLoner copy() {
        return new IllTemperedLoner(this);
    }
}
