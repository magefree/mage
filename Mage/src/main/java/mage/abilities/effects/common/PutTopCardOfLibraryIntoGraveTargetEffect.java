
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class PutTopCardOfLibraryIntoGraveTargetEffect extends OneShotEffect {

    private DynamicValue numberCards;

    public PutTopCardOfLibraryIntoGraveTargetEffect(int numberCards) {
        this(new StaticValue(numberCards));
    }

    public PutTopCardOfLibraryIntoGraveTargetEffect(DynamicValue numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.staticText = setText();
    }

    public PutTopCardOfLibraryIntoGraveTargetEffect(final PutTopCardOfLibraryIntoGraveTargetEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public PutTopCardOfLibraryIntoGraveTargetEffect copy() {
        return new PutTopCardOfLibraryIntoGraveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, numberCards.calculate(game, source, this)), Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("target player puts the top ");
        if (numberCards.toString().equals("1")) {
            sb.append(" card");
        } else {
            sb.append(CardUtil.numberToText(numberCards.toString()));
            sb.append(" cards");
        }
        sb.append(" of their library into their graveyard");
        return sb.toString();
    }
}
