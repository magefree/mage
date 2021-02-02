
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author Styxo
 */
public final class DoomedDissenter extends CardImpl {

    public DoomedDissenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Doomed Dissenter dies, create a 2/2 black Zombie creature token.        
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieToken())));
    }

    private DoomedDissenter(final DoomedDissenter card) {
        super(card);
    }

    @Override
    public DoomedDissenter copy() {
        return new DoomedDissenter(this);
    }
}
