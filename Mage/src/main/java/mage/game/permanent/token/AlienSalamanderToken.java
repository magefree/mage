package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class AlienSalamanderToken extends TokenImpl {

    public AlienSalamanderToken() {
        super("Alien Salamander Token", "2/2 green Alien Salamander creature token with islandwalk");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.SALAMANDER);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(new IslandwalkAbility());
    }

    protected AlienSalamanderToken(final AlienSalamanderToken token) {
        super(token);
    }

    public AlienSalamanderToken copy() {
        return new AlienSalamanderToken(this);
    }
}
