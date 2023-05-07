package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class DeadlyGrubInsectToken extends TokenImpl {

    public DeadlyGrubInsectToken() {
        super("Insect Token", "6/1 green Insect creature token with shroud");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(6);
        toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
    }

    public DeadlyGrubInsectToken(final DeadlyGrubInsectToken token) {
        super(token);
    }

    public DeadlyGrubInsectToken copy() {
        return new DeadlyGrubInsectToken(this);
    }
}
