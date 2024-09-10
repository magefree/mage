package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

public class BrainstormEffect extends OneShotEffect {

    private final int toDraw;
    private final int toPutOnTop;

    public BrainstormEffect() {
        this(3, 2);
    }

    public BrainstormEffect(int toDraw, int toPutOnTop) {
        super(Outcome.DrawCard);
        this.toDraw = toDraw;
        this.toPutOnTop = toPutOnTop;
        staticText = "draw " + CardUtil.numberToText(toDraw) + (toDraw > 1 ? " cards" : " card")
                + ", then put " + CardUtil.numberToText(toPutOnTop) + (toPutOnTop > 1 ? " cards" : " card")
                + " from your hand on top of your library in any order";
    }

    protected BrainstormEffect(final BrainstormEffect effect) {
        super(effect);
        this.toDraw = effect.toDraw;
        this.toPutOnTop = effect.toPutOnTop;
    }

    @Override
    public BrainstormEffect copy() {
        return new BrainstormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(toDraw, source, game);
            for (int i = 0; i < toPutOnTop; ++i) {
                putOnLibrary(player, source, game);
            }
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)) {
            player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }
        return false;
    }
}
