
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author cbt33
 */
public final class ChatterOfTheSquirrel extends CardImpl {

    public ChatterOfTheSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");


        // Create a 1/1 green Squirrel creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken()));
        
        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{1}{G}")));
    }

    private ChatterOfTheSquirrel(final ChatterOfTheSquirrel card) {
        super(card);
    }

    @Override
    public ChatterOfTheSquirrel copy() {
        return new ChatterOfTheSquirrel(this);
    }
}
