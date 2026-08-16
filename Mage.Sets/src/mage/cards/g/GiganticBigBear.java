package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GiganticBigBear extends CardImpl {

    public GiganticBigBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(7);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private GiganticBigBear(final GiganticBigBear card) {
        super(card);
    }

    @Override
    public GiganticBigBear copy() {
        return new GiganticBigBear(this);
    }
}
