
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author Backfir3
 */
public final class Hush extends CardImpl {

    public Hush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Hush(final Hush card) {
        super(card);
    }

    @Override
    public Hush copy() {
        return new Hush(this);
    }
}
