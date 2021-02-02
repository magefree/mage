
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SoulSummons extends CardImpl {

    public SoulSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Manifest the top card of your library. (Put it onto the battlefield face down as a 2/2 creature. Turn it face up at any time for its mana cost if it's a creature card.)
        this.getSpellAbility().addEffect(new ManifestEffect(1));
    }

    private SoulSummons(final SoulSummons card) {
        super(card);
    }

    @Override
    public SoulSummons copy() {
        return new SoulSummons(this);
    }
}


