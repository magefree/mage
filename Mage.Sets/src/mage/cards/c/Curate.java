package mage.cards.c;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class Curate extends CardImpl {

    public Curate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Look at the top two cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(2, Integer.MAX_VALUE, PutCards.GRAVEYARD, PutCards.TOP_ANY));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Curate(final Curate card) {
        super(card);
    }

    @Override
    public Curate copy() {
        return new Curate(this);
    }
}
