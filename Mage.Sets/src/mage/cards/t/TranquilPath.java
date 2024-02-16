
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author anonymous
 */
public final class TranquilPath extends CardImpl {

    public TranquilPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Destroy all enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private TranquilPath(final TranquilPath card) {
        super(card);
    }

    @Override
    public TranquilPath copy() {
        return new TranquilPath(this);
    }
}
