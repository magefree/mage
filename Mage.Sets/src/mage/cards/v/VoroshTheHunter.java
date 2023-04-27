
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class VoroshTheHunter extends CardImpl {

    public VoroshTheHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Vorosh, the Hunter deals combat damage to a player, you may pay {2}{G}. If you do, put six +1/+1 counters on Vorosh.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(6), true), new ManaCostsImpl<>("{2}{G}")), false));
    }

    private VoroshTheHunter(final VoroshTheHunter card) {
        super(card);
    }

    @Override
    public VoroshTheHunter copy() {
        return new VoroshTheHunter(this);
    }
}
