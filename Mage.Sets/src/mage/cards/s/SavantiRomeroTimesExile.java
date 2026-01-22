package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavantiRomeroTimesExile extends CardImpl {

    public SavantiRomeroTimesExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, put a +1/+1 counter on Savanti Romero. Then you draw X cards and lose X life, where X is the number of counters on Savanti Romero.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new DrawCardSourceControllerEffect(CountersSourceCount.ANY).setText("Then you draw X cards"));
        ability.addEffect(new LoseLifeSourceControllerEffect(CountersSourceCount.ANY)
                .setText("and lose X life, where X is the number of counters on {this}"));
        this.addAbility(ability);
    }

    private SavantiRomeroTimesExile(final SavantiRomeroTimesExile card) {
        super(card);
    }

    @Override
    public SavantiRomeroTimesExile copy() {
        return new SavantiRomeroTimesExile(this);
    }
}
