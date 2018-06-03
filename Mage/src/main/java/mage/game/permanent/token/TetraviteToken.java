

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.StaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author spjspj
 */
public final class TetraviteToken extends TokenImpl {

    public TetraviteToken() {
        super("Tetravite", "1/1 colorless Tetravite artifact creature token");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TETRAVITE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CantBeEnchantedAbility());
    }

    public TetraviteToken(final TetraviteToken token) {
        super(token);
    }

    public TetraviteToken copy() {
        return new TetraviteToken(this);
    }
}

class CantBeEnchantedAbility extends StaticAbility {

    public CantBeEnchantedAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public CantBeEnchantedAbility(final CantBeEnchantedAbility ability) {
        super(ability);
    }

    @Override
    public CantBeEnchantedAbility copy() {
        return new CantBeEnchantedAbility(this);
    }

    public boolean canTarget(MageObject source, Game game) {
        return !(source.isEnchantment()
                && source.hasSubtype(SubType.AURA, game));
    }

}
