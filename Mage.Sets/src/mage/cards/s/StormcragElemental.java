
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class StormcragElemental extends CardImpl {

    public StormcragElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Megamorph {4}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}{R}"), true));
    }

    private StormcragElemental(final StormcragElemental card) {
        super(card);
    }

    @Override
    public StormcragElemental copy() {
        return new StormcragElemental(this);
    }
}
