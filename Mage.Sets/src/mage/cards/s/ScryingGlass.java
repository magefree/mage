package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class ScryingGlass extends CardImpl {
    
    public ScryingGlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {tap}: Choose a number greater than 0 and a color. Target opponent reveals their hand. If that opponent reveals exactly the chosen number of cards of the chosen color, you draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryingGlassEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
    }
    
    private ScryingGlass(final ScryingGlass card) {
        super(card);
    }
    
    @Override
    public ScryingGlass copy() {
        return new ScryingGlass(this);
    }
}

class ScryingGlassEffect extends OneShotEffect {
    
    public ScryingGlassEffect() {
        super(Outcome.Neutral);
        staticText = "Choose a number greater than 0 and a color. Target opponent reveals their hand. If that opponent reveals exactly the chosen number of cards of the chosen color, you draw a card";
    }
    
    public ScryingGlassEffect(final ScryingGlassEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        ChoiceColor color = new ChoiceColor();
        int amount = 0;
        if (controller != null
                && targetOpponent != null) {
            amount = controller.getAmount(1, Integer.MAX_VALUE, "Choose a number", game);
            controller.choose(Outcome.Discard, color, game);
            FilterCard filter = new FilterCard();
            filter.add(new ColorPredicate(color.getColor()));
            targetOpponent.revealCards(source, targetOpponent.getHand(), game);
            if (targetOpponent.getHand().count(filter, game) == amount) {
                game.informPlayers(controller.getLogName() + " has chosen the exact number and color of the revealed cards from " + targetOpponent.getName() + "'s hand. They draw a card.");
                controller.drawCards(1, source, game);
                return true;
            } else {
                game.informPlayers(controller.getLogName() + " has chosen incorrectly and will not draw a card.");
            }
        }
        return false;
    }
    
    @Override
    public ScryingGlassEffect copy() {
        return new ScryingGlassEffect(this);
    }
}
