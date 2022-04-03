
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CrudeRampart extends CardImpl {

    public CrudeRampart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Morph {4}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{W}")));
    }

    private CrudeRampart(final CrudeRampart card) {
        super(card);
    }

    @Override
    public CrudeRampart copy() {
        return new CrudeRampart(this);
    }
}
