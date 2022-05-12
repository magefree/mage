
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SootfeatherFlock extends CardImpl {

    public SootfeatherFlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {3}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{B}")));
    }

    private SootfeatherFlock(final SootfeatherFlock card) {
        super(card);
    }

    @Override
    public SootfeatherFlock copy() {
        return new SootfeatherFlock(this);
    }
}
