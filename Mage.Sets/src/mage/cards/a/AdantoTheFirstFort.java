
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author TheElk801
 */
public final class AdantoTheFirstFort extends CardImpl {

    public AdantoTheFirstFort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        this.nightCard = true;

        // T: Add W.
        this.addAbility(new WhiteManaAbility());

        // 2W, T: Create a 1/1 white Vampire creature token with lifelink.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new IxalanVampireToken()), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AdantoTheFirstFort(final AdantoTheFirstFort card) {
        super(card);
    }

    @Override
    public AdantoTheFirstFort copy() {
        return new AdantoTheFirstFort(this);
    }
}
