package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class PutOnTopOrBottomLibraryTargetEffect extends OneShotEffect {

    private final int position;
    private final boolean textOwnerOf;

    public PutOnTopOrBottomLibraryTargetEffect(boolean textOwnerOf) {
        this(1, textOwnerOf);
    }

    public PutOnTopOrBottomLibraryTargetEffect(int position, boolean textOwnerOf) {
        super(Outcome.ReturnToHand);
        this.position = position;
        this.textOwnerOf = textOwnerOf;
    }

    private PutOnTopOrBottomLibraryTargetEffect(final PutOnTopOrBottomLibraryTargetEffect effect) {
        super(effect);
        this.position = effect.position;
        this.textOwnerOf = effect.textOwnerOf;
    }

    @Override
    public PutOnTopOrBottomLibraryTargetEffect copy() {
        return new PutOnTopOrBottomLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        String message = position > 1 ? "into your library " + CardUtil.numberToOrdinalText(position) + " from the top or on the" : "on the top or";
        boolean onTop = player.chooseUse(
                Outcome.Detriment, "Put the targeted object " + message + " bottom of your library?",
                null, "Top", "Bottom", source, game
        );
        if (onTop && position > 1) {
            return new PutIntoLibraryNFromTopTargetEffect(position).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(onTop).apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String targetText = getTargetPointer().describeTargets(mode.getTargets(), "that permanent");
        if (textOwnerOf) {
            sb.append("the owner of ");
            sb.append(targetText);
        } else {
            sb.append(targetText);
            sb.append("'s owner");
        }
        sb.append(" puts it");
        if (position > 1) {
            sb.append("into their library ");
            sb.append(CardUtil.numberToOrdinalText(position));
            sb.append(" from the top or on the bottom");
        } else {
            sb.append("on their choice of the top or bottom of their library");
        }
        return sb.toString();
    }
}
