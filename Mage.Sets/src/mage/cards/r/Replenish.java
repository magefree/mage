package mage.cards.r;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentCard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Replenish extends CardImpl {

    private static final FilterEnchantmentCard filter = new FilterEnchantmentCard("enchantment cards");

    public Replenish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Return all enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter));
    }

    private Replenish(final Replenish card) {
        super(card);
    }

    @Override
    public Replenish copy() {
        return new Replenish(this);
    }
}
