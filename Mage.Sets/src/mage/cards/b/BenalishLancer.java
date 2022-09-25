
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author FenrisulfrX
 */
public final class BenalishLancer extends CardImpl {

    public BenalishLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // If Benalish Lancer was kicked, it enters the battlefield with two +1/+1 counters on it and with first strike.
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it and with first strike.", "");
        ability.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private BenalishLancer(final BenalishLancer card) {
        super(card);
    }

    @Override
    public BenalishLancer copy() {
        return new BenalishLancer(this);
    }
}
