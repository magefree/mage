
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */
public class LookLibraryControllerEffect extends OneShotEffect {

    protected DynamicValue numberOfCards;
    protected boolean mayShuffleAfter = false;
    protected boolean putOnTop = true; // if false on put rest back on bottom of library
    protected Zone targetZoneLookedCards; // GRAVEYARD, LIBRARY
    protected boolean backInRandomOrder = false;

    public LookLibraryControllerEffect() {
        this(1);
    }

    public LookLibraryControllerEffect(int numberOfCards) {
        this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards) {
        this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter) {
        this(numberOfCards, mayShuffleAfter, true);
    }

    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
        this(StaticValue.get(numberOfCards), mayShuffleAfter, putOnTop);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
        this(Outcome.Benefit, numberOfCards, mayShuffleAfter, Zone.LIBRARY, putOnTop);
    }

    public LookLibraryControllerEffect(Outcome outcome, DynamicValue numberOfCards, boolean mayShuffleAfter, Zone targetZoneLookedCards, boolean putOnTop) {
        super(outcome);
        this.numberOfCards = numberOfCards;
        this.mayShuffleAfter = mayShuffleAfter;
        this.targetZoneLookedCards = targetZoneLookedCards;
        this.putOnTop = putOnTop;
    }

    public LookLibraryControllerEffect(final LookLibraryControllerEffect effect) {
        super(effect);
        this.numberOfCards = effect.numberOfCards.copy();
        this.mayShuffleAfter = effect.mayShuffleAfter;
        this.targetZoneLookedCards = effect.targetZoneLookedCards;
        this.putOnTop = effect.putOnTop;
        this.backInRandomOrder = effect.backInRandomOrder;
    }

    @Override
    public LookLibraryControllerEffect copy() {
        return new LookLibraryControllerEffect(this);

    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // take cards from library and look at them
        boolean topCardRevealed = controller.isTopCardRevealed();
        controller.setTopCardRevealed(false);
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, this.numberOfCards.calculate(game, source, this)));

        controller.lookAtCards(source, null, cards, game);

        this.actionWithSelectedCards(cards, game, source);

        this.putCardsBack(source, controller, cards, game);

        controller.setTopCardRevealed(topCardRevealed);

        this.mayShuffle(controller, source, game);

        return true;
    }

    public boolean isBackInRandomOrder() {
        return backInRandomOrder;
    }

    public Effect setBackInRandomOrder(boolean backInRandomOrder) {
        this.backInRandomOrder = backInRandomOrder;
        return this;
    }

    protected void actionWithSelectedCards(Cards cards, Game game, Ability source) {
    }

    /**
     * Put the rest of the cards back to defined zone
     *
     * @param source
     * @param player
     * @param cards
     * @param game
     */
    protected void putCardsBack(Ability source, Player player, Cards cards, Game game) {
        switch (targetZoneLookedCards) {
            case LIBRARY:
                if (putOnTop) {
                    player.putCardsOnTopOfLibrary(cards, game, source, !backInRandomOrder);
                } else {
                    player.putCardsOnBottomOfLibrary(cards, game, source, !backInRandomOrder);
                }
                break;
            case GRAVEYARD:
                player.moveCards(cards, Zone.GRAVEYARD, source, game);
                break;
            default:
            // not supported yet
        }
    }

    /**
     * Check to shuffle library if allowed
     *
     * @param player
     * @param source
     * @param game
     */
    protected void mayShuffle(Player player, Ability source, Game game) {
        if (this.mayShuffleAfter && player.chooseUse(Outcome.Benefit, "Shuffle your library?", source, game)) {
            player.shuffleLibrary(source, game);
        }
    }

    @Override
    public String getText(Mode mode) {
        return setText(mode, "");
    }

    public String setText(Mode mode, String middleText) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        int numberLook;
        try {
            numberLook = Integer.parseInt(numberOfCards.toString());
        } catch (NumberFormatException e) {
            numberLook = 0;
        }
        StringBuilder sb = new StringBuilder("look at the top ");
        switch (numberLook) {
            case 0:
                sb.append(" X ");
                break;
            case 1:
                sb.append("card ");
                break;
            default:
                sb.append(CardUtil.numberToText(numberLook));
                break;
        }
        if (numberLook != 1) {
            sb.append(" cards ");
        }

        sb.append("of your library");
        if (numberLook == 0) {
            sb.append(", where {X} is the number of cards ").append(numberOfCards.getMessage());
        }

        if (!middleText.isEmpty()) {
            sb.append(middleText);
        } else if (numberLook > 1) {
            if (backInRandomOrder) {
                sb.append(". Put the rest on the bottom of your library in a random order");
            } else {
                sb.append(", then put them back in any order");
            }
        }
        if (this.mayShuffleAfter) {
            sb.append(". You may shuffle");
        }

        return sb.toString();
    }
}
