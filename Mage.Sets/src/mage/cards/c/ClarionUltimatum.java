
package mage.cards.c;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class ClarionUltimatum extends CardImpl {

    public ClarionUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}{G}{W}{W}{W}{U}{U}");

        // Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ClarionUltimatumEffect());
    }

    public ClarionUltimatum(final ClarionUltimatum card) {
        super(card);
    }

    @Override
    public ClarionUltimatum copy() {
        return new ClarionUltimatum(this);
    }
}

class ClarionUltimatumEffect extends OneShotEffect {

    public ClarionUltimatumEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library";
    }

    public ClarionUltimatumEffect(final ClarionUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public ClarionUltimatumEffect copy() {
        return new ClarionUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        int permanentsCount = game.getBattlefield().getAllActivePermanents(source.getControllerId()).size();
        if (controller == null || permanentsCount < 1) {
            return false;
        }

        TargetControlledPermanent permanentsTarget = new TargetControlledPermanent(Math.min(permanentsCount, 5));
        controller.choose(Outcome.Benefit, permanentsTarget, source.getSourceId(), game);

        Set<Card> chosenCards = new LinkedHashSet<>();
        List<String> namesFiltered = new ArrayList<>();
        List<UUID> permanents = permanentsTarget.getTargets();
        for (UUID permanentId : permanents) {
            Permanent permanent = game.getPermanent(permanentId);
            final String cardName = permanent.getName();
            if (!namesFiltered.contains(cardName)) {
                if (controller.chooseUse(Outcome.PutCardInPlay, "Search for " + cardName + " in your library?", source, game)) {
                    FilterCard filter = new FilterCard("card named " + cardName);
                    filter.add(new NamePredicate(cardName));
                    TargetCardInLibrary target = new TargetCardInLibrary(filter);
                    if (controller.searchLibrary(target, game)) {
                        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                        if (card != null) {
                            chosenCards.add(card);
                        }
                    }
                } else {
                    namesFiltered.add(cardName);
                }
            }
        }

        controller.moveCards(chosenCards, Zone.BATTLEFIELD, source, game, true, false, false, null);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
