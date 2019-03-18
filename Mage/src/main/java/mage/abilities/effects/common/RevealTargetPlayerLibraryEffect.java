
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Luna Skyrise
 */
public class RevealTargetPlayerLibraryEffect extends OneShotEffect {

    private DynamicValue amountCards;

    public RevealTargetPlayerLibraryEffect(int amountCards) {
        this(new StaticValue(amountCards));
    }

    public RevealTargetPlayerLibraryEffect(DynamicValue amountCards) {
        super(Outcome.Neutral);
        this.amountCards = amountCards;
        this.staticText = setText();
    }

    public RevealTargetPlayerLibraryEffect(final RevealTargetPlayerLibraryEffect effect) {
        super(effect);
        this.amountCards = effect.amountCards;
    }

    @Override
    public RevealTargetPlayerLibraryEffect copy() {
        return new RevealTargetPlayerLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || targetPlayer == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(player.getLibrary().getTopCards(game, amountCards.calculate(game, source, this)));
        player.revealCards(sourceObject.getIdName() + " - Top " + amountCards.toString() + "cards of " + targetPlayer.getName() + "\'s library", cards, game);
        return true;
    }

    private String setText() {
        String number = amountCards.toString();
        StringBuilder sb = new StringBuilder("Reveal the top ");
        if ("1".equals(number)) {
            sb.append("card");
        } else {
            sb.append(CardUtil.numberToText(number)).append(" cards");
        }
        sb.append(" of target player's library.");
        return sb.toString();
    }
}
