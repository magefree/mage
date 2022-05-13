package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author weirddan455
 */
public class CreateTokenAttachSourceEffect extends CreateTokenEffect {

    public CreateTokenAttachSourceEffect(Token token) {
        super(token);
        staticText = staticText.concat(", then attach {this} to it");
    }

    private CreateTokenAttachSourceEffect(final CreateTokenAttachSourceEffect effect) {
        super(effect);
    }

    @Override
    public CreateTokenAttachSourceEffect copy() {
        return new CreateTokenAttachSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        super.apply(game, source);
        Permanent token = game.getPermanent(this.getLastAddedTokenIds().stream().findFirst().orElse(null));
        if (token != null) {
            token.addAttachment(source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
