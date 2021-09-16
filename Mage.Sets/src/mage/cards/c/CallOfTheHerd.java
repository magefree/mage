
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.ElephantToken;

/**
 *
 * @author Loki
 */
public final class CallOfTheHerd extends CardImpl {

    public CallOfTheHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Create a 3/3 green Elephant creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));
        // Flashback {3}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{G}")));
    }

    private CallOfTheHerd(final CallOfTheHerd card) {
        super(card);
    }

    @Override
    public CallOfTheHerd copy() {
        return new CallOfTheHerd(this);
    }
}
