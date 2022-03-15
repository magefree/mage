package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class SoulStairExpedition extends CardImpl {

    public SoulStairExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Soul Stair Expedition.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));

        // Remove three quest counters from Soul Stair Expedition and sacrifice it: Return up to two target creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)),
                        new SacrificeSourceCost(),
                        "Remove three quest counters from {this} and sacrifice it"
                )
        );
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private SoulStairExpedition(final SoulStairExpedition card) {
        super(card);
    }

    @Override
    public SoulStairExpedition copy() {
        return new SoulStairExpedition(this);
    }
}
