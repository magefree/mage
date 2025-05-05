package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Cards;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class BecomePermanentFacedownEffect extends ContinuousEffectImpl {

    public interface PermanentApplier {
        void apply(Permanent permanent, Game game, Ability source);
    }

    private final PermanentApplier applier;

    public BecomePermanentFacedownEffect(PermanentApplier applier, Cards cards, Game game) {
        super(Duration.Custom, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.Neutral);
        this.applier = applier;
        this.setTargetPointer(new FixedTargets(
                cards.getCards(game)
                        .stream()
                        .map(card -> new MageObjectReference(card, game, 1))
                        .collect(Collectors.toSet())
        ));
    }

    private BecomePermanentFacedownEffect(final BecomePermanentFacedownEffect effect) {
        super(effect);
        this.applier = effect.applier;
    }

    @Override
    public BecomePermanentFacedownEffect copy() {
        return new BecomePermanentFacedownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isFaceDown(game))
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            discard();
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.removeAllSuperTypes(game);
            permanent.removeAllCardTypes(game);
            permanent.removeAllSubTypes(game);
            applier.apply(permanent, game, source);
        }
        return true;
    }
}
