package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Humongulus extends CardImpl {

    public Humongulus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private Humongulus(final Humongulus card) {
        super(card);
    }

    @Override
    public Humongulus copy() {
        return new Humongulus(this);
    }
}
