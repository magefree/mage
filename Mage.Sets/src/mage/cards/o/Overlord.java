package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Overlord extends CardImpl {

    public Overlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Zerg creature spells you cast cost {1} less to cast.
        // Tap another untapped creature you control: Overlord gets +1/+0 until end of turn.
    }

    public Overlord(final Overlord card) {
        super(card);
    }

    @Override
    public Overlord copy() {
        return new Overlord(this);
    }
}
