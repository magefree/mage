
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class SidisisPet extends CardImpl {

    public SidisisPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.APE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        
        // Morph {1}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private SidisisPet(final SidisisPet card) {
        super(card);
    }

    @Override
    public SidisisPet copy() {
        return new SidisisPet(this);
    }
}
