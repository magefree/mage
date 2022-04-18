
package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class BatteringCraghorn extends CardImpl {

    public BatteringCraghorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GOAT, SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Morph {1}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{R}{R}")));
    }

    private BatteringCraghorn(final BatteringCraghorn card) {
        super(card);
    }

    @Override
    public BatteringCraghorn copy() {
        return new BatteringCraghorn(this);
    }
}
