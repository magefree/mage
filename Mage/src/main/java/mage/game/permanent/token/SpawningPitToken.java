

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SpawningPitToken extends TokenImpl {

    public SpawningPitToken() {
        super("Spawn Token", "2/2 colorless Spawn artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SPAWN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public SpawningPitToken(final SpawningPitToken token) {
        super(token);
    }

    public SpawningPitToken copy() {
        return new SpawningPitToken(this);
    }
}
