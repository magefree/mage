package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class InsidiousDreams extends CardImpl {

    public InsidiousDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // As an additional cost to cast Insidious Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));

        // Search your library for X cards. Then shuffle your library and put those cards on top of it in any order.
        this.getSpellAbility().addEffect(new InsidiousDreamsEffect());

    }

    private InsidiousDreams(final InsidiousDreams card) {
        super(card);
    }

    @Override
    public InsidiousDreams copy() {
        return new InsidiousDreams(this);
    }
}

class InsidiousDreamsEffect extends OneShotEffect {

    static final private String textTop = "card to put on your library (last chosen will be on top)";

    public InsidiousDreamsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X cards. Then shuffle and put those cards on top of it in any order";
    }

    public InsidiousDreamsEffect(final InsidiousDreamsEffect effect) {
        super(effect);
    }

    @Override
    public InsidiousDreamsEffect copy() {
        return new InsidiousDreamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        int amount = GetXValue.instance.calculate(game, source, this);

        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard());
            if (controller.searchLibrary(target, source, game)) {
                Cards chosen = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().remove(cardId, game);
                    chosen.add(card);
                }
                controller.shuffleLibrary(source, game);

                TargetCard targetToLib = new TargetCard(Zone.LIBRARY, new FilterCard(textTop));

                while (chosen.size() > 1 && controller.canRespond()) {
                    controller.choose(Outcome.Neutral, chosen, targetToLib, game);
                    Card card = chosen.get(targetToLib.getFirstTarget(), game);
                    if (card != null) {
                        chosen.remove(card);
                        controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, true, false);

                    }
                    targetToLib.clearChosen();
                }

                if (chosen.size() == 1) {
                    Card card = chosen.get(chosen.iterator().next(), game);
                    controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
