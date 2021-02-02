

package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BackToNature extends CardImpl {

    public BackToNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
    }

    private BackToNature(final BackToNature card) {
        super(card);
    }

    @Override
    public BackToNature copy() {
        return new BackToNature(this);
    }

}
