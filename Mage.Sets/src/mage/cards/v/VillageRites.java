package mage.cards.v;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VillageRites extends CardImpl {

    public VillageRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private VillageRites(final VillageRites card) {
        super(card);
    }

    @Override
    public VillageRites copy() {
        return new VillageRites(this);
    }
}
