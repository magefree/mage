
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
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
 *
 */
public final class PrisonBarricade extends CardImpl {

    public PrisonBarricade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // If Prison Barricade was kicked, it enters the battlefield with a +1/+1 counter on it and with "Prison Barricade can attack as though it didn't have defender."
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with a +1/+1 counter on it and with \"{this} can attack as though it didn't have defender.\"", "");
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private PrisonBarricade(final PrisonBarricade card) {
        super(card);
    }

    @Override
    public PrisonBarricade copy() {
        return new PrisonBarricade(this);
    }
}
