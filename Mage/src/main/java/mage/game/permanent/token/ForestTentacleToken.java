package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class ForestTentacleToken extends TokenImpl {

    public ForestTentacleToken() {
        super("Forest Tentacle", "Forest Tentacle token");
        cardType.add(CardType.LAND);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FOREST);
        subtype.add(SubType.TENTACLE);
        color.setGreen(true);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new GreenManaAbility());
    }

    private ForestTentacleToken(final ForestTentacleToken token) {
        super(token);
    }

    public ForestTentacleToken copy() {
        return new ForestTentacleToken(this);
    }
}
