
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class MisthoofKirin extends CardImpl {

    public MisthoofKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KIRIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Megamorph {1}{W} <i>(You may cast this face down as a 2/2 creature for {3}. Turn it face up any time for its megamorph cost and put a +1/+1 counter on it.)</i>
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{W}"), true));
    }

    private MisthoofKirin(final MisthoofKirin card) {
        super(card);
    }

    @Override
    public MisthoofKirin copy() {
        return new MisthoofKirin(this);
    }
}
