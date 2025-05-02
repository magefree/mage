package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RebelStarshipToken extends TokenImpl {

    public RebelStarshipToken() {
        super("B-Wing Token", "2/3 blue Rebel Starship artifact creature tokens with spaceflight name B-Wing");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        abilities.add(SpaceflightAbility.getInstance());
        color.setBlue(true);
        subtype.add(SubType.REBEL);
        subtype.add(SubType.STARSHIP);
    }

    private RebelStarshipToken(final RebelStarshipToken token) {
        super(token);
    }

    public RebelStarshipToken copy() {
        return new RebelStarshipToken(this);
    }
}
