
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class CongregationAtDawn extends CardImpl {

    public CongregationAtDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{G}{W}");


        // Search your library for up to three creature cards and reveal them. Shuffle your library, then put those cards on top of it in any order.
        this.getSpellAbility().addEffect(new CongregationAtDawnEffect());
    }

    private CongregationAtDawn(final CongregationAtDawn card) {
        super(card);
    }

    @Override
    public CongregationAtDawn copy() {
        return new CongregationAtDawn(this);
    }
}

class CongregationAtDawnEffect extends OneShotEffect {
    static final private String textTop = "card to put on your library (last chosen will be on top)";

    public CongregationAtDawnEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for up to three creature cards, reveal them, " +
                "then shuffle and put those cards on top in any order";
    }

    public CongregationAtDawnEffect(final CongregationAtDawnEffect effect) {
        super(effect);
    }

    @Override
    public CongregationAtDawnEffect copy() {
        return new CongregationAtDawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 3, new FilterCreatureCard("creature cards"));
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards revealed = new CardsImpl();
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        revealed.add(card);
                    }
                    controller.revealCards(sourceObject.getName(), revealed, game);
                    controller.shuffleLibrary(source, game);

                    TargetCard targetToLib = new TargetCard(Zone.LIBRARY, new FilterCard(textTop));

                    while (revealed.size() > 1 && controller.canRespond()) {
                        controller.choose(Outcome.Neutral, revealed, targetToLib, game);
                        Card card = revealed.get(targetToLib.getFirstTarget(), game);
                        if (card != null) {
                            revealed.remove(card);
                            controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, true, false);

                        }
                        targetToLib.clearChosen();
                    }

                    if (revealed.size() == 1) {
                        Card card = revealed.get(revealed.iterator().next(), game);
                        controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, true, false);
                    }

                }

                return true;
            }
        }
        return false;
    }
}
