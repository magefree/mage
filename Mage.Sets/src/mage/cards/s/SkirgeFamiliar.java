
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SkirgeFamiliar extends CardImpl {

    public SkirgeFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Discard a card: Add {B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new DiscardCardCost(false)));
    }

    public SkirgeFamiliar(final SkirgeFamiliar card) {
        super(card);
    }

    @Override
    public SkirgeFamiliar copy() {
        return new SkirgeFamiliar(this);
    }
}
