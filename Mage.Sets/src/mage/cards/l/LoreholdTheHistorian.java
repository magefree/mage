package mage.cards.l;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdTheHistorian extends CardImpl {

    public LoreholdTheHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Each instant and sorcery card in your hand has miracle {2}.
        this.addAbility(new SimpleStaticAbility(new LoreholdTheHistorianEffect()));
        this.addAbility(new LoreholdTheHistorianMiracleTriggeredAbility(), new CardsAmountDrawnThisTurnWatcher());

        // At the beginning of each opponent's upkeep, you may discard a card. If you do, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT,
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
                false
        ));
    }

    private LoreholdTheHistorian(final LoreholdTheHistorian card) {
        super(card);
    }

    @Override
    public LoreholdTheHistorian copy() {
        return new LoreholdTheHistorian(this);
    }
}

class LoreholdTheHistorianEffect extends ContinuousEffectImpl {

    LoreholdTheHistorianEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each instant and sorcery card in your hand has miracle {2}";
    }

    private LoreholdTheHistorianEffect(final LoreholdTheHistorianEffect effect) {
        super(effect);
    }

    @Override
    public LoreholdTheHistorianEffect copy() {
        return new LoreholdTheHistorianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class LoreholdTheHistorianMiracleTriggeredAbility extends TriggeredAbilityImpl {

    LoreholdTheHistorianMiracleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoreholdTheHistorianMiracleEffect(), true);
        setTriggerPhrase("Whenever you draw your first card each turn, if it's an instant or sorcery card, ");
    }

    private LoreholdTheHistorianMiracleTriggeredAbility(final LoreholdTheHistorianMiracleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoreholdTheHistorianMiracleTriggeredAbility copy() {
        return new LoreholdTheHistorianMiracleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        Card card = game.getCard(event.getTargetId());
        if (watcher == null
                || watcher.getAmountCardsDrawn(event.getPlayerId()) != 1
                || card == null
                || !StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(card, game));
        return true;
    }
}

class LoreholdTheHistorianMiracleEffect extends OneShotEffect {

    private final ManaCosts<ManaCost> miracleCosts = new ManaCostsImpl<>("{2}");

    LoreholdTheHistorianMiracleEffect() {
        super(Outcome.Benefit);
        staticText = "you may reveal that card and cast it for its miracle cost";
    }

    private LoreholdTheHistorianMiracleEffect(final LoreholdTheHistorianMiracleEffect effect) {
        super(effect);
    }

    @Override
    public LoreholdTheHistorianMiracleEffect copy() {
        return new LoreholdTheHistorianMiracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        controller.revealCards("Miracle", new CardsImpl(card), game);
        SpellAbility abilityToCast = card.getSpellAbility().copy();
        ManaCosts<ManaCost> costRef = abilityToCast.getManaCostsToPay();
        costRef.clear();
        costRef.add(miracleCosts);
        controller.cast(abilityToCast, game, false, new ApprovingObject(source, game));
        return true;
    }
}
