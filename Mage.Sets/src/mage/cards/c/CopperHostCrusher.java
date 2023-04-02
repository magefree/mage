package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CopperHostCrusher extends CardImpl {

    public CopperHostCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private CopperHostCrusher(final CopperHostCrusher card) {
        super(card);
    }

    @Override
    public CopperHostCrusher copy() {
        return new CopperHostCrusher(this);
    }
}
