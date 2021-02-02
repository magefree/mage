
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class CrumbleToDust extends CardImpl {

    public CrumbleToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Exile target nonbasic land. Search its controller's graveyard, hand, and library for any number of cards with the same name as that land and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetNonBasicLandPermanent());
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's", "any number of cards with the same name as that land"));
    }

    private CrumbleToDust(final CrumbleToDust card) {
        super(card);
    }

    @Override
    public CrumbleToDust copy() {
        return new CrumbleToDust(this);
    }
}
