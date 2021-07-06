package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteadfastPaladin extends CardImpl {

    public SteadfastPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private SteadfastPaladin(final SteadfastPaladin card) {
        super(card);
    }

    @Override
    public SteadfastPaladin copy() {
        return new SteadfastPaladin(this);
    }
}
