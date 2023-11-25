package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledEnchantmentPermanent;

/**
 *
 * @author icetc
 */
public final class Simplify extends CardImpl {

    private static final FilterControlledEnchantmentPermanent filter = new FilterControlledEnchantmentPermanent("an enchantment");

    public Simplify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Each player sacrifices an enchantment.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(1, filter));
    }

    private Simplify(final Simplify card) {
        super(card);
    }

    @Override
    public Simplify copy() {
        return new Simplify(this);
    }
}
