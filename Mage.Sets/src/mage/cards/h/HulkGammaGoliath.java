package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HulkGammaGoliath extends CardImpl {

    public HulkGammaGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Power-up abilities of other creatures you control cost {3} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(
            PowerUpAbility.class, "", 3, true
        ).setText("power-up abilities of other creatures you control cost {3} less to activate")));

        // Power-up -- {6}{R}{G}: Put five +1/+1 counters on Hulk.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
            new ManaCostsImpl<>("{6}{R}{G}")
        ));
    }

    private HulkGammaGoliath(final HulkGammaGoliath card) {
        super(card);
    }

    @Override
    public HulkGammaGoliath copy() {
        return new HulkGammaGoliath(this);
    }
}
