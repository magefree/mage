package mage.cards.e;

import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author cbt33, jeffwadsworth (Supreme Inquisitor)
 */
public final class Extract extends CardImpl {

    public Extract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Search target player's library for a card and exile it. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new SearchLibraryAndExileTargetEffect(1, false));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Extract(final Extract card) {
        super(card);
    }

    @Override
    public Extract copy() {
        return new Extract(this);
    }
}
