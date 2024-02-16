
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nickymikail
 */
public final class LurchingRotbeast extends CardImpl {

    public LurchingRotbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Cycling {B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));

    }

    private LurchingRotbeast(final LurchingRotbeast card) {
        super(card);
    }

    @Override
    public LurchingRotbeast copy() {
        return new LurchingRotbeast(this);
    }
}
