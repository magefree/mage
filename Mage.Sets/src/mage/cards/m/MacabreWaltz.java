
package mage.cards.m;


import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MacabreWaltz extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature cards from your graveyard");

    public MacabreWaltz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return up to two target creature cards from your graveyard to your hand, then discard a card.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1).setText(", then discard a card"));
    }

    public MacabreWaltz(final MacabreWaltz card) {
        super(card);
    }

    @Override
    public MacabreWaltz copy() {
        return new MacabreWaltz(this);
    }
}
