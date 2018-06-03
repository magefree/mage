
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class DawnToDusk extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("enchantment card from your graveyard");
    static {
        filterCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public DawnToDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}");


        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Return target enchantment card from your graveyard to your hand;
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filterCard));
        // and/or destroy target enchantment.
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
    }

    public DawnToDusk(final DawnToDusk card) {
        super(card);
    }

    @Override
    public DawnToDusk copy() {
        return new DawnToDusk(this);
    }
}
