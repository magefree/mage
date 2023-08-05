package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerazolTheSplitCurrent extends CardImpl {

    public VerazolTheSplitCurrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Verazol, the Split Current enters the battlefield with a +1/+1 counter on it for each mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ManaSpentToCastCount.instance, true
        ), "with a +1/+1 counter on it for each mana spent to cast it"));

        // Whenever you cast a kicked spell, you may remove two +1/+1 counters from Verazol, the Split Current. If you do, copy that spell. You may choose new targets for that copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new CopyTargetSpellEffect(false, true)
                                .setText("copy that spell. You may choose new targets for the copy"),
                        new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2))
                ), StaticFilters.FILTER_SPELL_KICKED_A, false, true
        ));
    }

    private VerazolTheSplitCurrent(final VerazolTheSplitCurrent card) {
        super(card);
    }

    @Override
    public VerazolTheSplitCurrent copy() {
        return new VerazolTheSplitCurrent(this);
    }
}
