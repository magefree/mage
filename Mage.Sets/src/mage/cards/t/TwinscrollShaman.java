package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinscrollShaman extends CardImpl {

    public TwinscrollShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private TwinscrollShaman(final TwinscrollShaman card) {
        super(card);
    }

    @Override
    public TwinscrollShaman copy() {
        return new TwinscrollShaman(this);
    }
}
