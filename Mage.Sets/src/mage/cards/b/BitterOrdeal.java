package mage.cards.b;

import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.abilities.keyword.GravestormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BitterOrdeal extends CardImpl {

    public BitterOrdeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Search target player's library for a card and exile it. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new SearchLibraryAndExileTargetEffect(1, false));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Gravestorm
        this.addAbility(new GravestormAbility());
    }

    private BitterOrdeal(final BitterOrdeal card) {
        super(card);
    }

    @Override
    public BitterOrdeal copy() {
        return new BitterOrdeal(this);
    }
}
