package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author notgreat
 */
public class LookAtOpponentFaceDownCreaturesAnyTimeEffect extends ContinuousEffectImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face-down creatures you don't control");

    static {
        filter.add(FaceDownPredicate.instance);
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public LookAtOpponentFaceDownCreaturesAnyTimeEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public LookAtOpponentFaceDownCreaturesAnyTimeEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = (duration.toString().isEmpty() ? "" : duration.toString() + ", ") + "you may look at face-down creatures you don't control any time";
    }

    protected LookAtOpponentFaceDownCreaturesAnyTimeEffect(final LookAtOpponentFaceDownCreaturesAnyTimeEffect effect) {
        super(effect);
    }

    //Based on LookAtTopCardOfLibraryAnyTimeEffect
    @Override
    public boolean apply(Game game, Ability source) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return false;
        }
        controller.lookAtCards(source, "Face Down Creatures", cards, game);
        return true;
    }

    @Override
    public LookAtOpponentFaceDownCreaturesAnyTimeEffect copy() {
        return new LookAtOpponentFaceDownCreaturesAnyTimeEffect(this);
    }
}
