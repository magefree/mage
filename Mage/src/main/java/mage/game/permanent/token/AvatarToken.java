

package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class AvatarToken extends TokenImpl {

    public AvatarToken() {
        super("Avatar", "white Avatar creature token with \"This creature's power and toughness are each equal to your life total.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AVATAR);
        color.setWhite(true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AvatarTokenEffect()));
    }

    public AvatarToken(final AvatarToken token) {
        super(token);
    }

    public AvatarToken copy() {
        return new AvatarToken(this);
    }
}

class AvatarTokenEffect extends ContinuousEffectImpl {

    public AvatarTokenEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
    }

    public AvatarTokenEffect(final AvatarTokenEffect effect) {
        super(effect);
    }

    @Override
    public AvatarTokenEffect copy() {
        return new AvatarTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent token = game.getPermanent(source.getSourceId());
        if (token != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                token.getPower().setValue(controller.getLife());
                token.getToughness().setValue(controller.getLife());
                return true;
            }
        }
        return false;
    }

}
