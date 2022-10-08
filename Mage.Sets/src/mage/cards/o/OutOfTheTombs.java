package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OutOfTheTombs extends CardImpl {

    public OutOfTheTombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, put two eon counters on Out of the Tombs, then mill cards equal to the number of eon counters on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.EON.createInstance(2)), TargetController.YOU, false);
        ability.addEffect(new MillCardsControllerEffect(new CountersSourceCount(CounterType.EON)).concatBy(", then").setText("mill cards equal to the number of eon counters on it"));
        this.addAbility(ability);

        // If you would draw a card while your library has no cards in it, instead return a creature card from your graveyard to the battlefield. If you can’t, you lose the game.
        this.addAbility(new SimpleStaticAbility(new OutOfTheTombsReplacementEffect()));
    }

    private OutOfTheTombs(final OutOfTheTombs card) {
        super(card);
    }

    @Override
    public OutOfTheTombs copy() {
        return new OutOfTheTombs(this);
    }
}

class OutOfTheTombsReplacementEffect extends ReplacementEffectImpl {

    public OutOfTheTombsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card while your library has no cards in it, instead return a creature card " +
                "from your graveyard to the battlefield. If you can't, you lose the game.";
    }

    public OutOfTheTombsReplacementEffect(final OutOfTheTombsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public OutOfTheTombsReplacementEffect copy() {
        return new OutOfTheTombsReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }
        boolean cardReturned = false;
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (target.canChoose(player.getId(), source, game)) {
            if (target.choose(Outcome.PutCreatureInPlay, player.getId(), source.getSourceId(), source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);
                    cardReturned = true;
                }
            }
        }
        if (!cardReturned) {
            game.informPlayers(player.getLogName() + " can't return a card from graveyard to hand.");
            player.lost(game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            return player != null && !player.hasLost() && !player.getLibrary().hasCards();
        }
        return false;
    }
}
