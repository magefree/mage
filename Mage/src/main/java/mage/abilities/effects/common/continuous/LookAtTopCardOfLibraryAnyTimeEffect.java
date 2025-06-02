package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

import java.util.Collections;
import java.util.List;

/**
 * @author TheElk801
 */
public class LookAtTopCardOfLibraryAnyTimeEffect extends ContinuousEffectImpl {

    public LookAtTopCardOfLibraryAnyTimeEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public LookAtTopCardOfLibraryAnyTimeEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = (duration.toString().isEmpty() ? "" : duration.toString() + ", ") +
                "you may look at the top card of your library any time";
    }

    protected LookAtTopCardOfLibraryAnyTimeEffect(final LookAtTopCardOfLibraryAnyTimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Card)) {
                continue;
            }
            Player controller = game.getPlayer(((Card) object).getOwnerId());
            if (controller == null) {
                return false;
            }
            controller.lookAtCards("Top card of your library", (Card) object, game);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return Collections.emptyList();
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !canLookAtNextTopLibraryCard(game)) {
            return Collections.emptyList();
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        return topCard != null ? Collections.singletonList(topCard) : Collections.emptyList();
    }

    @Override
    public LookAtTopCardOfLibraryAnyTimeEffect copy() {
        return new LookAtTopCardOfLibraryAnyTimeEffect(this);
    }
}
