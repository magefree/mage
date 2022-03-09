
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class Manamorphose extends CardImpl {

    public Manamorphose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R/G}");


        // Add two mana in any combination of colors.
        this.getSpellAbility().addEffect(new AddManaInAnyCombinationEffect(2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Manamorphose(final Manamorphose card) {
        super(card);
    }

    @Override
    public Manamorphose copy() {
        return new Manamorphose(this);
    }
}
