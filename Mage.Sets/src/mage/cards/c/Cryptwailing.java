
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2 & L_J
 */
public final class Cryptwailing extends CardImpl {

    public Cryptwailing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");

        // {1}, Exile two creature cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Cryptwailing(final Cryptwailing card) {
        super(card);
    }

    @Override
    public Cryptwailing copy() {
        return new Cryptwailing(this);
    }
}
