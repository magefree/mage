package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BitingPalmNinja extends CardImpl {

    public BitingPalmNinja(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ninjutsu {2}{B}
        this.addAbility(new NinjutsuAbility("{2}{B}"));

        // Biting-Palm Ninja enters the battlefield with a menace counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.MENACE.createInstance(1)
        ), "with a menace counter on it"));

        // Whenever Biting-Palm Ninja deals combat damage to a player, you may remove a menace counter from it. When you do, that player reveals their hand and you choose a nonland card from it. Exile that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoWhenCostPaid(new ReflexiveTriggeredAbility(
                        new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND), false,
                        "that player reveals their hand and you choose a nonland card from it. Exile that card"
                ), new RemoveCountersSourceCost(CounterType.MENACE.createInstance()).setText("remove a menace counter from it"), "Remove a menace counter?"), false, true
        ));
    }

    private BitingPalmNinja(final BitingPalmNinja card) {
        super(card);
    }

    @Override
    public BitingPalmNinja copy() {
        return new BitingPalmNinja(this);
    }
}
