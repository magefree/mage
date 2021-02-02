

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 * @author Loki
 */
public final class Cleanfall extends CardImpl {

    public Cleanfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
    }

    private Cleanfall(final Cleanfall card) {
        super(card);
    }

    @Override
    public Cleanfall copy() {
        return new Cleanfall(this);
    }

}