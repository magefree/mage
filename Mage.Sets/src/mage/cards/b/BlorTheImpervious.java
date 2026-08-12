package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class BlorTheImpervious extends CardImpl {

    public BlorTheImpervious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private BlorTheImpervious(final BlorTheImpervious card) {
        super(card);
    }

    @Override
    public BlorTheImpervious copy() {
        return new BlorTheImpervious(this);
    }
}
