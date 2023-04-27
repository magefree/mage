
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class OketrasAttendant extends CardImpl {

    public OketrasAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

        // Embalm {3}{W}{W}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{W}{W}"), this));

    }

    private OketrasAttendant(final OketrasAttendant card) {
        super(card);
    }

    @Override
    public OketrasAttendant copy() {
        return new OketrasAttendant(this);
    }
}
