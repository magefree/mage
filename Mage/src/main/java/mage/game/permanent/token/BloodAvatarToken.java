package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author TheElk801
 */
public final class BloodAvatarToken extends TokenImpl {

    public BloodAvatarToken() {
        super("Avatar Token", "3/6 black and red Avatar creature token with haste and \"Whenever this creature attacks, it deals 3 damage to each opponent.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.AVATAR);
        power = new MageInt(3);
        toughness = new MageInt(6);

        addAbility(HasteAbility.getInstance());
        addAbility(new AttacksTriggeredAbility(
                new DamagePlayersEffect(3, TargetController.OPPONENT), false,
                "Whenever this creature attacks, it deals 3 damage to each opponent."
        ));
    }

    private BloodAvatarToken(final BloodAvatarToken token) {
        super(token);
    }

    public BloodAvatarToken copy() {
        return new BloodAvatarToken(this);
    }
}
