package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RotisserieElemental extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.SKEWER);

    public RotisserieElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Rotisserie Elemental deals combat damage to a player, put a skewer counter on Rotisserie Elemental. Then you may sacrifice it. If you do, exile the top X cards of your library, where X is the number of skewer counters on Rotisserie Elemental. You may play those cards this turn.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.SKEWER.createInstance()),
                false
        ).withRuleTextReplacement(false);
        ability.addEffect(new DoIfCostPaid(
                new ExileTopXMayPlayUntilEndOfTurnEffect(xValue)
                        .setText("exile the top X cards of your library, where X is the number of skewer counters "
                                + "on {this}. You may play those cards this turn"),
                new SacrificeSourceCost().setText("sacrifice it")
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private RotisserieElemental(final RotisserieElemental card) {
        super(card);
    }

    @Override
    public RotisserieElemental copy() {
        return new RotisserieElemental(this);
    }
}
