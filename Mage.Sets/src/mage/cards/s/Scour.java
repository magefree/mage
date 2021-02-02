
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class Scour extends CardImpl {

    public Scour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");


        // Exile target enchantment.
        // Search its controller's graveyard, hand, and library for all cards with the same name as that enchantment and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's","all cards with the same name as that enchantment"));
    }

    private Scour(final Scour card) {
        super(card);
    }

    @Override
    public Scour copy() {
        return new Scour(this);
    }
}
