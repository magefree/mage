package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * 702.52. Transmute
 *
 * 702.52a Transmute is an activated ability that functions only while the card
 * with transmute is in a player's hand. “Transmute [cost]” means “[Cost],
 * Discard this card: Search your library for a card with the same converted
 * mana cost as the discarded card, reveal that card, and put it into your hand.
 * Then shuffle your library. Play this ability only any time you could play a
 * sorcery.”
 *
 * 702.52b Although the transmute ability is playable only if the card is in a
 * player's hand, it continues to exist while the object is in play and in all
 * other zones. Therefore objects with transmute will be affected by effects
 * that depend on objects having one or more activated abilities.
 *
 * @author Loki
 */
public class TransmuteAbility extends SimpleActivatedAbility {

    public TransmuteAbility(String manaCost) {
        super(Zone.HAND, new TransmuteEffect(), new ManaCostsImpl(manaCost));
        this.setTiming(TimingRule.SORCERY);
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
        return new StringBuilder("Transmute ").append(this.getManaCosts().getText())
                .append(" <i>(").append(this.getManaCosts().getText())
                .append(", Discard this card: Search your library for a card with the same mana value as this card, reveal it, and put it into your hand. Then shuffle. Transmute only as a sorcery.)</i>").toString();
    }
}

class TransmuteEffect extends OneShotEffect {

    TransmuteEffect() {
        super(Outcome.Benefit);
        staticText = "Transmute";
    }

    TransmuteEffect(final TransmuteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            FilterCard filter = new FilterCard("card with mana value " + sourceObject.getManaValue());
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, sourceObject.getManaValue()));
            TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards revealed = new CardsImpl(target.getTargets());
                    controller.revealCards(sourceObject.getIdName(), revealed, game);
                    controller.moveCards(revealed, Zone.HAND, source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }

        return false;
    }

    @Override
    public TransmuteEffect copy() {
        return new TransmuteEffect(this);
    }
}
