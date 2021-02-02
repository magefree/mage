
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jonubuu
 */
public final class Reshape extends CardImpl {

    public Reshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // As an additional cost to cast Reshape, sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact"), false)));
        
        // Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(new FilterArtifactCard()));
    }

    private Reshape(final Reshape card) {
        super(card);
    }

    @Override
    public Reshape copy() {
        return new Reshape(this);
    }
}
