
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class StripedRiverwinder extends CardImpl {

    public StripedRiverwinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));

    }

    private StripedRiverwinder(final StripedRiverwinder card) {
        super(card);
    }

    @Override
    public StripedRiverwinder copy() {
        return new StripedRiverwinder(this);
    }
}
