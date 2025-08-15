package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurtleSeals extends CardImpl {

    public TurtleSeals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.SEAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private TurtleSeals(final TurtleSeals card) {
        super(card);
    }

    @Override
    public TurtleSeals copy() {
        return new TurtleSeals(this);
    }
}
