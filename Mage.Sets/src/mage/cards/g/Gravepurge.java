
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class Gravepurge extends CardImpl {

    public Gravepurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");


        // Put any number of target creature cards from your graveyard on top of your library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Gravepurge(final Gravepurge card) {
        super(card);
    }

    @Override
    public Gravepurge copy() {
        return new Gravepurge(this);
    }
}
