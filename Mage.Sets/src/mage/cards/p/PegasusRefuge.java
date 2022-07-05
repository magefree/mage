
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.PegasusToken;

/**
 *
 * @author LoneFox
 */
public final class PegasusRefuge extends CardImpl {

    public PegasusRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // {2}, Discard a card: Create a 1/1 white Pegasus creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PegasusToken()), new ManaCostsImpl<>("{2}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private PegasusRefuge(final PegasusRefuge card) {
        super(card);
    }

    @Override
    public PegasusRefuge copy() {
        return new PegasusRefuge(this);
    }
}
