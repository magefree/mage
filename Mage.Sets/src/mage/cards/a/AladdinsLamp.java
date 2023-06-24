
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author MarcoMarin
 */
public final class AladdinsLamp extends CardImpl {

    public AladdinsLamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{10}");

        // {X}, {T}: The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AladdinsLampEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        for (Object cost : ability.getManaCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
            }
        }
        this.addAbility(ability);
    }

    private AladdinsLamp(final AladdinsLamp card) {
        super(card);
    }

    @Override
    public AladdinsLamp copy() {
        return new AladdinsLamp(this);
    }
}

class AladdinsLampEffect extends ReplacementEffectImpl {

    public AladdinsLampEffect() {
        super(Duration.EndOfTurn, Outcome.DrawCard);
        staticText = "The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.";
    }

    public AladdinsLampEffect(final AladdinsLampEffect effect) {
        super(effect);
    }

    @Override
    public AladdinsLampEffect copy() {
        return new AladdinsLampEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
        controller.lookAtCards(source, null, cards, game);
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to stay at the top of library"));
        if (controller.choose(outcome, cards, target, source, game)) {
            cards.remove(target.getFirstTarget());
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        game.getState().processAction(game);
        controller.drawCards(1, source, game, event);
        discard();
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
