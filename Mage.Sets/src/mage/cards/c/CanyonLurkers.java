
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CanyonLurkers extends CardImpl {

    public CanyonLurkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Morph 3R
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{R}")));
    }

    private CanyonLurkers(final CanyonLurkers card) {
        super(card);
    }

    @Override
    public CanyonLurkers copy() {
        return new CanyonLurkers(this);
    }
}
