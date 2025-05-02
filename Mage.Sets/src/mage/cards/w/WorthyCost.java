package mage.cards.w;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorthyCost extends CardImpl {

    public WorthyCost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private WorthyCost(final WorthyCost card) {
        super(card);
    }

    @Override
    public WorthyCost copy() {
        return new WorthyCost(this);
    }
}
