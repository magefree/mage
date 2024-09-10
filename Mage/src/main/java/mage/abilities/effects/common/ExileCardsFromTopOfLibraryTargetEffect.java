package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2, Susucr
 */
public class ExileCardsFromTopOfLibraryTargetEffect extends OneShotEffect {

    private final DynamicValue amount;

    public ExileCardsFromTopOfLibraryTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public ExileCardsFromTopOfLibraryTargetEffect(DynamicValue amount) {
        super(Outcome.Exile);
        this.amount = amount.copy();
    }

    protected ExileCardsFromTopOfLibraryTargetEffect(final ExileCardsFromTopOfLibraryTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public ExileCardsFromTopOfLibraryTargetEffect copy() {
        return new ExileCardsFromTopOfLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int milled = amount.calculate(game, source, this);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (milled > 0 && targetPlayer != null) {
            Cards cards = new CardsImpl();
            cards.addAllCards(targetPlayer.getLibrary().getTopCards(game, milled));
            return targetPlayer.moveCards(cards, Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + " exiles the top "
                + (amount.toString().equals("1") ? "card" : CardUtil.numberToText(amount.toString(), "a") + " cards")
                + " of their library";
    }
}
