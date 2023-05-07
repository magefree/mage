package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class CloudSpriteToken extends TokenImpl {

    public CloudSpriteToken() {
        super("Cloud Sprite", "1/1 blue Faerie creature token named Cloud Sprite. It has flying and \"Cloud Sprite can block only creatures with flying.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FAERIE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cloud Sprite can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    public CloudSpriteToken(final CloudSpriteToken token) {
        super(token);
    }

    public CloudSpriteToken copy() {
        return new CloudSpriteToken(this);
    }
}
