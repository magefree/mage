
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.DragonToken2;

/**
 *
 * @author Loki
 */
public final class DragonRoost extends CardImpl {

    public DragonRoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}{R}");

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DragonToken2(), 1), new ManaCostsImpl<>("{5}{R}{R}")));
    }

    private DragonRoost(final DragonRoost card) {
        super(card);
    }

    @Override
    public DragonRoost copy() {
        return new DragonRoost(this);
    }
}
