

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class SwanSongBirdToken extends TokenImpl {

    public SwanSongBirdToken() {
        super("Bird Token", "2/2 blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            setTokenType(2);
        }
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    public SwanSongBirdToken(final SwanSongBirdToken token) {
        super(token);
    }

    public SwanSongBirdToken copy() {
        return new SwanSongBirdToken(this);
    }
}
