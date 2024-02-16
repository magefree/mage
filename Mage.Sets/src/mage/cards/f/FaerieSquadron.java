
package mage.cards.f;


import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class FaerieSquadron extends CardImpl {

    public FaerieSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {3}{U}
        this.addAbility(new KickerAbility("{3}{U}"));
        // If Faerie Squadron was kicked, it enters the battlefield with two +1/+1 counters on it and with flying.
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it and with flying.", "");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private FaerieSquadron(final FaerieSquadron card) {
        super(card);
    }

    @Override
    public FaerieSquadron copy() {
        return new FaerieSquadron(this);
    }
}
