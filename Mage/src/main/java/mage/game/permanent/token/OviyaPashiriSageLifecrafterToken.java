package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class OviyaPashiriSageLifecrafterToken extends TokenImpl {

    public OviyaPashiriSageLifecrafterToken() {
        this(1);
    }

    public OviyaPashiriSageLifecrafterToken(int number) {
        super("Construct Token", "X/X colorless Construct artifact creature token, where X is the number of creatures you control");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(number);
        toughness = new MageInt(number);
    }

    public OviyaPashiriSageLifecrafterToken(final OviyaPashiriSageLifecrafterToken token) {
        super(token);
    }

    public OviyaPashiriSageLifecrafterToken copy() {
        return new OviyaPashiriSageLifecrafterToken(this);
    }
}
