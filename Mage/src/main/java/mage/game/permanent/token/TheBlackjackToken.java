package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class TheBlackjackToken extends TokenImpl {

    public TheBlackjackToken() {
        super("The Blackjack", "The Blackjack, a legendary 3/3 colorless Vehicle artifact token with flying and crew 2");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.VEHICLE);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CrewAbility(2));
    }

    private TheBlackjackToken(final TheBlackjackToken token) {
        super(token);
    }

    public TheBlackjackToken copy() {
        return new TheBlackjackToken(this);
    }
}
