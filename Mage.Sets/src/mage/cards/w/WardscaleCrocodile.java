package mage.cards.w;

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
public final class WardscaleCrocodile extends CardImpl {

    public WardscaleCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private WardscaleCrocodile(final WardscaleCrocodile card) {
        super(card);
    }

    @Override
    public WardscaleCrocodile copy() {
        return new WardscaleCrocodile(this);
    }
}
