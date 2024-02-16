
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class FlaringFlameKin extends CardImpl {

    public FlaringFlameKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as Flaring Flame-Kin is enchanted, it gets +2/+2, has trample, and has "{R}: Flaring Flame-Kin gets +1/+0 until end of turn."
        EnchantedSourceCondition enchanted = new EnchantedSourceCondition();
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), enchanted,
            "As long as {this} is enchanted, it gets +2/+2"));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(TrampleAbility.getInstance()), enchanted,
            ", has trample"));
        Ability grantedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(grantedAbility),
            enchanted, ", and has \"{R}: {this} gets +1/+0 until end of turn.\""));
        this.addAbility(ability);
    }

    private FlaringFlameKin(final FlaringFlameKin card) {
        super(card);
    }

    @Override
    public FlaringFlameKin copy() {
        return new FlaringFlameKin(this);
    }
}
