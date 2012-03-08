package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public class TransmuteAbility extends SimpleActivatedAbility {
    public TransmuteAbility(String manaCost) {
        super(Constants.Zone.HAND, new TransmuteEffect(), new ManaCostsImpl(manaCost));
        this.addCost(new DiscardSourceCost());
    }

    public TransmuteAbility(final TransmuteAbility ability) {
        super(ability);
    }

    @Override
    public SimpleActivatedAbility copy() {
        return new TransmuteAbility(this);
    }

    @Override
    public String getRule() {
        return "Transmute " + this.getManaCosts().getText();
    }
}

class TransmuteEffect extends OneShotEffect<TransmuteEffect> {
    TransmuteEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Transmute";
    }

    TransmuteEffect(final TransmuteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());

        if (sourceCard != null && player != null) {
            FilterCard filter = new FilterCard("card with converted mana cost " + sourceCard.getManaCost().convertedManaCost());
            filter.setConvertedManaCost(sourceCard.getManaCost().convertedManaCost());
            filter.setConvertedManaCostComparison(Filter.ComparisonType.Equal);
            TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
            if (player.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    Cards revealed = new CardsImpl();
                    for (UUID cardId : target.getTargets()) {
                        Card card = player.getLibrary().remove(cardId, game);
                        if (card != null) {
                            card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                            revealed.add(card);
                        }
                    }
                    player.revealCards("Search", revealed, game);
                }
            }
            player.shuffleLibrary(game);
            return true;
        }

        return false;
    }

    @Override
    public TransmuteEffect copy() {
        return new TransmuteEffect(this);
    }
}
