

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class CloudSpriteToken extends TokenImpl {

    public CloudSpriteToken() {
        super("Cloud Sprite", "1/1 blue faerie creature token named Cloud Sprite with flying and \"Cloud Sprite can block only creatures with flying.\"");
        this.setOriginalExpansionSetCode("FUT");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FAERIE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    public CloudSpriteToken(final CloudSpriteToken token) {
        super(token);
    }

    public CloudSpriteToken copy() {
        return new CloudSpriteToken(this);
    }
}
