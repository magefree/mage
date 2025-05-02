
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public final class AladdinsLamp extends CardImpl {

    public AladdinsLamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{10}");

        // {X}, {T}: The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.
        Ability ability = new SimpleActivatedAbility(new AladdinsLampEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(AladdinsLampCostAdjuster.instance);

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

    AladdinsLampEffect() {
        super(Duration.EndOfTurn, Outcome.DrawCard);
        staticText = "The next time you would draw a card this turn, instead look at the top X cards of your library, put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.";
    }

    private AladdinsLampEffect(final AladdinsLampEffect effect) {
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

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, CardUtil.getSourceCostsTag(game, source, "X", 0)));
        controller.lookAtCards(source, null, cards, game);
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to stay at the top of library"));
        if (controller.choose(outcome, cards, target, source, game)) {
            cards.remove(target.getFirstTarget());
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        game.processAction();
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

enum AladdinsLampCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareX(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }

        ability.setVariableCostsMinMax(1, Math.max(1, controller.getLibrary().size()));
    }
}
