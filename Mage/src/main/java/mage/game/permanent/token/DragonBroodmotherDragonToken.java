
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class DragonBroodmotherDragonToken extends TokenImpl {

    public DragonBroodmotherDragonToken() {
        super("Dragon Token", "1/1 red and green Dragon creature token with flying and devour 2");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(new DevourAbility(DevourEffect.DevourFactor.Devour2));
    }

    public DragonBroodmotherDragonToken(final DragonBroodmotherDragonToken token) {
        super(token);
    }

    public DragonBroodmotherDragonToken copy() {
        return new DragonBroodmotherDragonToken(this);
    }
}
