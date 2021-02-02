
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SkywingAven extends CardImpl {

    public SkywingAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Discard a card: Return Skywing Aven to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new ReturnToHandSourceEffect(),
            new DiscardCardCost()));
    }

    private SkywingAven(final SkywingAven card) {
        super(card);
    }

    @Override
    public SkywingAven copy() {
        return new SkywingAven(this);
    }
}
