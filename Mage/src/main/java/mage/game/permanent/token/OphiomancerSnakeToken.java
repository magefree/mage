package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class OphiomancerSnakeToken extends TokenImpl {

    public OphiomancerSnakeToken() {
        super("Snake Token", "1/1 black Snake creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private OphiomancerSnakeToken(final OphiomancerSnakeToken token) {
        super(token);
    }

    public OphiomancerSnakeToken copy() {
        return new OphiomancerSnakeToken(this);
    }
}
