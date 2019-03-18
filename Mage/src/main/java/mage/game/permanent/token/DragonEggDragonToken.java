
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class DragonEggDragonToken extends TokenImpl {

    public DragonEggDragonToken() {
        super("Dragon", "2/2 red Dragon creature token with flying that has \"{R}: This creature gets +1/+0 until end of turn");
        this.setOriginalExpansionSetCode("M14");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
        addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));

    }

    public DragonEggDragonToken(final DragonEggDragonToken token) {
        super(token);
    }

    public DragonEggDragonToken copy() {
        return new DragonEggDragonToken(this);
    }
}
