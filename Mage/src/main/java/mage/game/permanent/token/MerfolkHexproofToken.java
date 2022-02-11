package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TacomenX
 */

public final class MerfolkHexproofToken extends TokenImpl {
    
    public MerfolkHexproofToken() {
        super("Merfolk", "1/1 blue Merfolk creature token with hexproof");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.MERFOLK);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(HexproofAbility.getInstance());
    }

    public MerfolkHexproofToken(final MerfolkHexproofToken token) {
        super(token);
    }

    public MerfolkHexproofToken copy() {
        return new MerfolkHexproofToken(this);
    }
}
