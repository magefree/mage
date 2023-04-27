
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ChargingSlateback extends CardImpl {

    public ChargingSlateback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Charging Slateback can't block.
        this.addAbility(new CantBlockAbility());
        // Morph {4}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}")));
    }

    private ChargingSlateback(final ChargingSlateback card) {
        super(card);
    }

    @Override
    public ChargingSlateback copy() {
        return new ChargingSlateback(this);
    }
}
