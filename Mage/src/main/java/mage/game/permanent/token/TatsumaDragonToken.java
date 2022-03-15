

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class TatsumaDragonToken extends TokenImpl {

    public TatsumaDragonToken() {
        super("Dragon Spirit Token", "5/5 blue Dragon Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }

    public TatsumaDragonToken(final TatsumaDragonToken token) {
        super(token);
    }

    public TatsumaDragonToken copy() {
        return new TatsumaDragonToken(this);
    }
}
