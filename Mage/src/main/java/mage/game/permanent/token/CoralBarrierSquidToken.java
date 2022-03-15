

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;

/**
 *
 * @author spjspj
 */
public final class CoralBarrierSquidToken extends TokenImpl {

    public CoralBarrierSquidToken() {
        super("Squid Token", "1/1 blue Squid creature token with islandwalk");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SQUID);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
    }
    public CoralBarrierSquidToken(final CoralBarrierSquidToken token) {
        super(token);
    }

    public CoralBarrierSquidToken copy() {
        return new CoralBarrierSquidToken(this);
    }
}
