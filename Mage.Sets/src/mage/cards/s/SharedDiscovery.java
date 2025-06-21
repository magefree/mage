package mage.cards.s;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SharedDiscovery extends CardImpl {

    public SharedDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // As an additional cost to cast Shared Discovery, tap four untapped creatures you control.
        this.getSpellAbility().addCost(new TapTargetCost(4, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private SharedDiscovery(final SharedDiscovery card) {
        super(card);
    }

    @Override
    public SharedDiscovery copy() {
        return new SharedDiscovery(this);
    }
}
