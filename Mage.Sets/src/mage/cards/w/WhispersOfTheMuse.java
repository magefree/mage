
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class WhispersOfTheMuse extends CardImpl {

    public WhispersOfTheMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Buyback {5} (You may pay an additional {5} as you cast this spell. If you do, put this card into your hand as it resolves.)
        this.addAbility(new BuybackAbility("{5}"));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private WhispersOfTheMuse(final WhispersOfTheMuse card) {
        super(card);
    }

    @Override
    public WhispersOfTheMuse copy() {
        return new WhispersOfTheMuse(this);
    }
}