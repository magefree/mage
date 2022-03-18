package mage.cards.d;

import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DenyingWind extends CardImpl {

    public DenyingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // Search target player's library for up to seven cards and exile them. Then that player shuffles their library.
        getSpellAbility().addEffect(new SearchLibraryAndExileTargetEffect(7, true));
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private DenyingWind(final DenyingWind card) {
        super(card);
    }

    @Override
    public DenyingWind copy() {
        return new DenyingWind(this);
    }
}
