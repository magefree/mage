
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public final class EnshrinedMemories extends CardImpl {

    public EnshrinedMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Reveal the top X cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new RevealLibraryPutIntoHandEffect(new ManacostVariableValue(), new FilterCreatureCard(), Zone.LIBRARY, true));
    }

    public EnshrinedMemories(final EnshrinedMemories card) {
        super(card);
    }

    @Override
    public EnshrinedMemories copy() {
        return new EnshrinedMemories(this);
    }
}
