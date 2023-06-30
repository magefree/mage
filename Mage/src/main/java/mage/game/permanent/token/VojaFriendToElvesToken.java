package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class VojaFriendToElvesToken extends TokenImpl {

    public VojaFriendToElvesToken() {
        super("Voja, Friend to Elves", "Voja, Friend to Elves, a legendary 3/3 green and white Wolf creature token");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private VojaFriendToElvesToken(final VojaFriendToElvesToken token) {
        super(token);
    }

    public VojaFriendToElvesToken copy() {
        return new VojaFriendToElvesToken(this);
    }

}
