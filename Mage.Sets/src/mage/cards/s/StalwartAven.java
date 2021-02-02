
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class StalwartAven extends CardImpl {

    public StalwartAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Renown 1 <i>(When this creature deals combat damage to a player if it isn't renowned put a +1/+1 counter on it and it becomes renowned.
        this.addAbility(new RenownAbility(1));
    }

    private StalwartAven(final StalwartAven card) {
        super(card);
    }

    @Override
    public StalwartAven copy() {
        return new StalwartAven(this);
    }
}
