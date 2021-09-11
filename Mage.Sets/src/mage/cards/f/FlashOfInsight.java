
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class FlashOfInsight extends CardImpl {

    public FlashOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{1}{U}");

        // Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new FlashOfInsightEffect());

        // Flashback-{1}{U}, Exile X blue cards from your graveyard.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl("{1}{U}"));
        FilterCard filter = new FilterCard("blue cards from your graveyard");
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new CardIdPredicate(getId())));
        ability.addCost(new ExileXFromYourGraveCost(filter));
        this.addAbility(ability);
    }

    private FlashOfInsight(final FlashOfInsight card) {
        super(card);
    }

    @Override
    public FlashOfInsight copy() {
        return new FlashOfInsight(this);
    }
}

class FlashOfInsightEffect extends OneShotEffect {

    public FlashOfInsightEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public FlashOfInsightEffect(final FlashOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public FlashOfInsightEffect copy() {
        return new FlashOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        int xValue = source.getManaCostsToPay().getX();

        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                xValue = ((ExileFromGraveCost) cost).getExiledCards().size();
            }
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        controller.lookAtCards(sourceObject.getIdName(), cards, game);

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
        target.setNotTarget(true);
        if (controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
                cards.remove(card);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
