

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;

/**
 *
 * @author spjspj
 */
public final class ChasmSkulkerSquidToken extends TokenImpl {

    public ChasmSkulkerSquidToken() {
        super("Squid Token", "1/1 blue Squid creature token with islandwalk");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SQUID);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
    }

    public ChasmSkulkerSquidToken(final ChasmSkulkerSquidToken token) {
        super(token);
    }

    public ChasmSkulkerSquidToken copy() {
        return new ChasmSkulkerSquidToken(this);
    }
}
