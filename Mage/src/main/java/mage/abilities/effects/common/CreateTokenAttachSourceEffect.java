package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        staticText = staticText.concat(innerConcat + (optional ? ". You may" : "") + " attach {this} to it");
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
        Player player = game.getPlayer(source.getControllerId());
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (player == null
                || equipment == null
                || optional
                && !player.chooseUse(
                Outcome.BoostCreature, "Attach " +
                        equipment.getLogName() + " to the token?", source, game
        )) {
            return false;
        }
        List<Permanent> permanents = this
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Permanent token;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                token = permanents.get(0);
                break;
            default:
                FilterPermanent filter = new FilterPermanent("token");
                filter.add(new PermanentReferenceInCollectionPredicate(permanents, game));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                target.withChooseHint("to attach to");
                player.choose(outcome, target, source, game);
                token = game.getPermanent(target.getFirstTarget());
        }
        if (token != null) {
            token.addAttachment(source.getSourceId(), source, game);
        }
        return true;
    }
}
