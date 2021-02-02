
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author North
 */
public final class BeastHunt extends CardImpl {

    public BeastHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        this.getSpellAbility().addEffect(new RevealLibraryPutIntoHandEffect(3, new FilterCreatureCard("creature cards"), Zone.GRAVEYARD));
    }

    private BeastHunt(final BeastHunt card) {
        super(card);
    }

    @Override
    public BeastHunt copy() {
        return new BeastHunt(this);
    }
}
