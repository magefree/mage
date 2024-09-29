package mage.abilities.effects.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;

public class DrawCardForEachColorAmongControlledPermanentsEffect extends OneShotEffect {

    public DrawCardForEachColorAmongControlledPermanentsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each color among permanents you control";
    }

    protected DrawCardForEachColorAmongControlledPermanentsEffect(final DrawCardForEachColorAmongControlledPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public DrawCardForEachColorAmongControlledPermanentsEffect copy() {
        return new DrawCardForEachColorAmongControlledPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<ObjectColor> colors = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (permanent.getColor(game).isBlack()) {
                    colors.add(ObjectColor.BLACK);
                }
                if (permanent.getColor(game).isBlue()) {
                    colors.add(ObjectColor.BLUE);
                }
                if (permanent.getColor(game).isRed()) {
                    colors.add(ObjectColor.RED);
                }
                if (permanent.getColor(game).isGreen()) {
                    colors.add(ObjectColor.GREEN);
                }
                if (permanent.getColor(game).isWhite()) {
                    colors.add(ObjectColor.WHITE);
                }
            }
            controller.drawCards(colors.size(), source, game);
            return true;
        }
        return false;
    }
}
