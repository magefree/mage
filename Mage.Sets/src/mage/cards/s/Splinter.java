
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Splinter extends CardImpl {
    private static final FilterPermanent filter = new FilterArtifactPermanent();

    public Splinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");


        // Exile target artifact. Search its controller's graveyard, hand, and library for all cards
        // with the same name as that artifact and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's","all cards with the same name as that artifact"));
    }

    private Splinter(final Splinter card) {
        super(card);
    }

    @Override
    public Splinter copy() {
        return new Splinter(this);
    }
}
