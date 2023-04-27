
package mage.cards.s;

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
 * @author LoneFox
 */
public final class SpinedBasher extends CardImpl {

    public SpinedBasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Morph {2}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private SpinedBasher(final SpinedBasher card) {
        super(card);
    }

    @Override
    public SpinedBasher copy() {
        return new SpinedBasher(this);
    }
}
