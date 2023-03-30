
package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author LevelX2
 */
public final class RecrossThePaths extends CardImpl {

    public RecrossThePaths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal cards from the top of your library until you reveal a land card. Put that card onto the battlefield and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new RevealCardsFromLibraryUntilEffect(new FilterLandCard(), Zone.BATTLEFIELD, Zone.LIBRARY, false, true));

        // Clash with an opponent. If you win, return Recross the Paths to its owner's hand.
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(ReturnToHandSpellEffect.getInstance()));
    }

    private RecrossThePaths(final RecrossThePaths card) {
        super(card);
    }

    @Override
    public RecrossThePaths copy() {
        return new RecrossThePaths(this);
    }
}
