
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author emerald000
 */
public final class EmptyThePits extends CardImpl {

    public EmptyThePits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{X}{B}{B}{B}{B}");


        // Delve
        this.addAbility(new DelveAbility(false));
        
        // create X 2/2 black Zombie creature tokens tapped.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), GetXValue.instance, true, false));
    }

    private EmptyThePits(final EmptyThePits card) {
        super(card);
    }

    @Override
    public EmptyThePits copy() {
        return new EmptyThePits(this);
    }
}
