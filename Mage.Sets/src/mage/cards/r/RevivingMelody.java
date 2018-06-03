
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class RevivingMelody extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("enchantment card from your graveyard");

    static {
        filterCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public RevivingMelody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        //Return target creature card from your graveyard to your hand;
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        // and/or return target enchantment card from your graveyard to your hand.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(filterCard));
        this.getSpellAbility().addMode(mode);

    }

    public RevivingMelody(final RevivingMelody card) {
        super(card);
    }

    @Override
    public RevivingMelody copy() {
        return new RevivingMelody(this);
    }
}
