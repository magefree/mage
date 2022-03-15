

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class BroodKeeperDragonToken extends TokenImpl {

    public BroodKeeperDragonToken() {
        super("Dragon Token", "2/2 red Dragon creature token with flying. It has \"{R}: This creature gets +1/+0 until end of turn.\"");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    public BroodKeeperDragonToken(final BroodKeeperDragonToken token) {
        super(token);
    }

    public BroodKeeperDragonToken copy() {
        return new BroodKeeperDragonToken(this);
    }
}
