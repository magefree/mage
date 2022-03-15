
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class SekKuarDeathkeeperGravebornToken extends TokenImpl {

    public SekKuarDeathkeeperGravebornToken() {
        super("Graveborn Token", "3/1 black and red Graveborn creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.GRAVEBORN);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    public SekKuarDeathkeeperGravebornToken(final SekKuarDeathkeeperGravebornToken token) {
        super(token);
    }

    public SekKuarDeathkeeperGravebornToken copy() {
        return new SekKuarDeathkeeperGravebornToken(this);
    }
}
