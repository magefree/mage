package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class CharnelTroll extends CardImpl {

    public CharnelTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, exile a creature card from your graveyard. If you do, put a +1/+1 counter on Morgue Troll. Otherwise sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new SacrificeSourceEffect(),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(
                                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
                        )), false
                ).setText("exile a creature card from your graveyard. "
                        + "If you do, put a +1/+1 counter on {this}."
                        + " Otherwise, sacrifice it."),
                TargetController.YOU, false
        ));

        // {B}{G}, Discard a creature card: Put a +1/+1 counter on Morgue Troll.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl("{B}{G}")
        );
        ability.addCost(new DiscardTargetCost(
                new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A)
        ));
        this.addAbility(ability);
    }

    private CharnelTroll(final CharnelTroll card) {
        super(card);
    }

    @Override
    public CharnelTroll copy() {
        return new CharnelTroll(this);
    }
}
