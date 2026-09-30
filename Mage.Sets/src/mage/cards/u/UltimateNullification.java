package mage.cards.u;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class UltimateNullification extends CardImpl {

    public UltimateNullification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // As an additional cost to cast this spell, sacrifice a legendary creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CREATURE_LEGENDARY));

        // Exile all creatures and graveyards. Put Ultimate Nullification on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new ExileGraveyardAllPlayersEffect().setText("and graveyards"));
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private UltimateNullification(final UltimateNullification card) {
        super(card);
    }

    @Override
    public UltimateNullification copy() {
        return new UltimateNullification(this);
    }
}
