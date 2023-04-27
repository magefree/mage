
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

/**
 *
 * @author nantuko
 */
public final class ThinkTwice extends CardImpl {

    public ThinkTwice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{U}")));
    }

    private ThinkTwice(final ThinkTwice card) {
        super(card);
    }

    @Override
    public ThinkTwice copy() {
        return new ThinkTwice(this);
    }
}
