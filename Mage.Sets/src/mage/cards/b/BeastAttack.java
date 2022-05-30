
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.BeastToken2;

/**
 *
 * @author cbt33
 */
public final class BeastAttack extends CardImpl {

    public BeastAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}{G}");

        // Create a 4/4 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken2()));

        // Flashback {2}{G}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{G}{G}{G}")));
    }

    private BeastAttack(final BeastAttack card) {
        super(card);
    }

    @Override
    public BeastAttack copy() {
        return new BeastAttack(this);
    }
}
