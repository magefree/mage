
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class EtherealAmbush extends CardImpl {

    public EtherealAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}{U}");

        // Manifest the top two cards of your library.
        this.getSpellAbility().addEffect(new ManifestEffect(2));
    }

    private EtherealAmbush(final EtherealAmbush card) {
        super(card);
    }

    @Override
    public EtherealAmbush copy() {
        return new EtherealAmbush(this);
    }
}
