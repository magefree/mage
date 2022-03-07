package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class AvatarToken extends TokenImpl {

    public AvatarToken() {
        super("Avatar", "white Avatar creature token. It has \"This creature's power and toughness are each equal to your life total.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AVATAR);
        color.setWhite(true);
        this.addAbility(new SimpleStaticAbility(new SetPowerToughnessSourceEffect(
                ControllerLifeCount.instance, Duration.WhileOnBattlefield,
                SubLayer.CharacteristicDefining_7a
        ).setText("this creature's power and toughness are each equal to your life total")));
    }

    public AvatarToken(final AvatarToken token) {
        super(token);
    }

    public AvatarToken copy() {
        return new AvatarToken(this);
    }
}
