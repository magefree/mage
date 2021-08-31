package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class LookAtTopCardOfLibraryAnyTimeEffect extends ContinuousEffectImpl {

    private final TargetController targetLibrary;

    public LookAtTopCardOfLibraryAnyTimeEffect() {
        this(TargetController.YOU, Duration.WhileOnBattlefield);
    }

    public LookAtTopCardOfLibraryAnyTimeEffect(TargetController targetLibrary, Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.targetLibrary = targetLibrary;

        String libInfo;
        switch (this.targetLibrary) {
            case YOU:
                libInfo = "your library";
                break;
            case OPPONENT:
                libInfo = "opponents libraries";
                break;
            case SOURCE_TARGETS:
                libInfo = "target player's library";
                break;
            default:
                throw new IllegalArgumentException("Unknown target library type: " + targetLibrary);
        }
        staticText = duration.toString().isEmpty() ? "" : duration + " you may look at the top card of " + libInfo + " any time.";
    }

    protected LookAtTopCardOfLibraryAnyTimeEffect(final LookAtTopCardOfLibraryAnyTimeEffect effect) {
        super(effect);
        this.targetLibrary = effect.targetLibrary;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!canLookAtNextTopLibraryCard(game)) {
            return false;
        }
        MageObject obj = source.getSourceObject(game);
        if (obj == null) {
            return false;
        }

        Set<UUID> needPlayers = new HashSet<>();
        switch (this.targetLibrary) {
            case YOU: {
                needPlayers.add(source.getControllerId());
                break;
            }
            case OPPONENT: {
                needPlayers.addAll(game.getOpponents(source.getControllerId()));
                break;
            }
            case SOURCE_TARGETS: {
                needPlayers.addAll(CardUtil.getAllSelectedTargets(source, game));
                break;
            }
        }

        Set<Card> needCards = new HashSet<>();
        needPlayers.stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.getLibrary().getFromTop(game))
                .filter(Objects::nonNull)
                .forEach(needCards::add);
        if (needCards.isEmpty()) {
            return false;
        }

        // all fine, can show top card
        needCards.forEach(topCard -> {
            Player owner = game.getPlayer(topCard.getOwnerId());
            controller.lookAtCards(String.format("%s: top card of %s", obj.getName(), owner == null ? "error" : owner.getName()), topCard, game);
        });
        return true;
    }

    @Override
    public LookAtTopCardOfLibraryAnyTimeEffect copy() {
        return new LookAtTopCardOfLibraryAnyTimeEffect(this);
    }
}
