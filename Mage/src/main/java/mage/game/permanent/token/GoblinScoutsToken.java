

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;

/**
 *
 * @author spjspj
 */
public final class GoblinScoutsToken extends TokenImpl {

    public GoblinScoutsToken() {
        super("Goblin Scout Token", "1/1 red Goblin Scout creature tokens with mountainwalk");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.SCOUT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new MountainwalkAbility());
    }

    public GoblinScoutsToken(final GoblinScoutsToken token) {
        super(token);
    }

    public GoblinScoutsToken copy() {
        return new GoblinScoutsToken(this);
    }
}
