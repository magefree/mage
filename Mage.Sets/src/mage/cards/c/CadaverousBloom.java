
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterOwnedCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox
 */
public final class CadaverousBloom extends CardImpl {

    public CadaverousBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{G}");

        // Exile a card from your hand: Add {B}{B} or {G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ExileFromHandCost(new TargetCardInHand(new FilterOwnedCard("a card from your hand")))));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new ExileFromHandCost(new TargetCardInHand(new FilterOwnedCard("a card from your hand")))));
    }

    public CadaverousBloom(final CadaverousBloom card) {
        super(card);
    }

    @Override
    public CadaverousBloom copy() {
        return new CadaverousBloom(this);
    }
}
