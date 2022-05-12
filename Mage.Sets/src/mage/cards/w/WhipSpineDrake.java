
package mage.cards.w;

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
public final class WhipSpineDrake extends CardImpl {

    public WhipSpineDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {2}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{W}")));
    }

    private WhipSpineDrake(final WhipSpineDrake card) {
        super(card);
    }

    @Override
    public WhipSpineDrake copy() {
        return new WhipSpineDrake(this);
    }
}
