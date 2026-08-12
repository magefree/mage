package mage.game.permanent.token;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class NewPlanetToken extends TokenImpl {

    public NewPlanetToken() {
        super("New Planet", "Planet land token named New Planet with \"{T}: Add one mana of any color.\"");
        cardType.add(CardType.LAND);
        subtype.add(SubType.PLANET);

        this.addAbility(new AnyColorManaAbility());
    }

    private NewPlanetToken(final NewPlanetToken token) {
        super(token);
    }

    @Override
    public NewPlanetToken copy() {
        return new NewPlanetToken(this);
    }
}
