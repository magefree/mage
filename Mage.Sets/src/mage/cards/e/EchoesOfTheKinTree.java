
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class EchoesOfTheKinTree extends CardImpl {

    public EchoesOfTheKinTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // {2}{W}: Bolster 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BolsterEffect(1),new ManaCostsImpl<>("{2}{W}")));
    }

    private EchoesOfTheKinTree(final EchoesOfTheKinTree card) {
        super(card);
    }

    @Override
    public EchoesOfTheKinTree copy() {
        return new EchoesOfTheKinTree(this);
    }
}
