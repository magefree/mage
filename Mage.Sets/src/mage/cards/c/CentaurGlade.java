
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.CentaurToken;

/**
 *
 * @author fireshoes
 */
public final class CentaurGlade extends CardImpl {

    public CentaurGlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}");

        // {2}{G}{G}: Create a 3/3 green Centaur creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CentaurToken(), 1), new ManaCostsImpl<>("{2}{G}{G}")));
    }

    private CentaurGlade(final CentaurGlade card) {
        super(card);
    }

    @Override
    public CentaurGlade copy() {
        return new CentaurGlade(this);
    }
}
