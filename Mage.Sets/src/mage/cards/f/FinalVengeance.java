package mage.cards.f;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinalVengeance extends CardImpl {

    public FinalVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or enchantment.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FinalVengeance(final FinalVengeance card) {
        super(card);
    }

    @Override
    public FinalVengeance copy() {
        return new FinalVengeance(this);
    }
}
