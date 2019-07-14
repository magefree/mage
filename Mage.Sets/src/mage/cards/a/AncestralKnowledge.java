
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author emerald000
 */
public final class AncestralKnowledge extends CardImpl {

    public AncestralKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // When Ancestral Knowledge enters the battlefield, look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AncestralKnowledgeEffect()));

        // When Ancestral Knowledge leaves the battlefield, shuffle your library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ShuffleLibrarySourceEffect(), false));
    }

    public AncestralKnowledge(final AncestralKnowledge card) {
        super(card);
    }

    @Override
    public AncestralKnowledge copy() {
        return new AncestralKnowledge(this);
    }
}

class AncestralKnowledgeEffect extends OneShotEffect {

    AncestralKnowledgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order";
    }

    AncestralKnowledgeEffect(final AncestralKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public AncestralKnowledgeEffect copy() {
        return new AncestralKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 10));
            if (!cards.isEmpty()) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, new FilterCard("cards to exile"));
                controller.choose(Outcome.Exile, cards, target, game);
                Cards toExile = new CardsImpl(target.getTargets());
                controller.moveCards(toExile, Zone.EXILED, source, game);
                cards.removeAll(toExile);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
