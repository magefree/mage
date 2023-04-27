
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AerieBowmasters extends CardImpl {

    public AerieBowmasters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        
        // Reach <i>(This creature can block creatures with flying.)</i>
        this.addAbility(ReachAbility.getInstance());
        
        // Megamorph {5}{G} <i>(You may cast this card face down as a 2/2 creature for {3}. Turn it face up at any time for its megamorph cost and put a +1/+1 counter on it.)</i>)
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{G}"), true));


    }

    private AerieBowmasters(final AerieBowmasters card) {
        super(card);
    }

    @Override
    public AerieBowmasters copy() {
        return new AerieBowmasters(this);
    }
}
