package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.Optional;

/**
 * @author weirddan455
 */
public class CreateTokenAttachSourceEffect extends CreateTokenEffect {

    private final boolean optional;

    public CreateTokenAttachSourceEffect(Token token) {
        this(token, ", then");
    }

    public CreateTokenAttachSourceEffect(Token token, String innerConcat) {
        this(token, innerConcat, false);
    }

    public CreateTokenAttachSourceEffect(Token token, String innerConcat, boolean optional) {
        super(token);
        this.optional = optional;
        staticText = staticText.concat(innerConcat + (optional ? " you may" : "") + " attach {this} to it");
    }

    private CreateTokenAttachSourceEffect(final CreateTokenAttachSourceEffect effect) {
        super(effect);
        this.optional = effect.optional;
    }

    @Override
    public CreateTokenAttachSourceEffect copy() {
        return new CreateTokenAttachSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        super.apply(game, source);
        Permanent token = this
                .getLastAddedTokenIds()
                .stream()
                .findFirst()
                .map(game::getPermanent)
                .orElse(null);
        if (token == null || optional
                && !Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .map(player -> player.chooseUse(
                        Outcome.BoostCreature, "Attach the equipment to the token?", source, game
                ))
                .orElse(false)) {
            return false;
        }
        token.addAttachment(source.getSourceId(), source, game);
        return true;
    }
}
