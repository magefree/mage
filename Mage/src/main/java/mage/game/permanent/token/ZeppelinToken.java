package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class ZeppelinToken extends TokenImpl {

    public ZeppelinToken() {
        super("Zeppelin", "5/5 colorless Vehicle artifact token named Zeppelin with flying and crew 3");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.VEHICLE);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CrewAbility(3));

        availableImageSetCodes = Arrays.asList("J22");
    }

    public ZeppelinToken(final ZeppelinToken token) {
        super(token);
    }

    public ZeppelinToken copy() {
        return new ZeppelinToken(this);
    }
}
