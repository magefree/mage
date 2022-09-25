
package mage.cards.p;


import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox

 */
public final class PouncingKavu extends CardImpl {

    public PouncingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {2}{R}
        this.addAbility(new KickerAbility("{2}{R}"));
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // If Pouncing Kavu was kicked, it enters the battlefield with two +1/+1 counters on it and with haste.
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it and with haste.", "");
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private PouncingKavu(final PouncingKavu card) {
        super(card);
    }

    @Override
    public PouncingKavu copy() {
        return new PouncingKavu(this);
    }
}
