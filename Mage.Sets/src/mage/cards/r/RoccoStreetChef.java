package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoccoStreetChef extends CardImpl {

    public RoccoStreetChef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, each player exiles the top card of their library. Until your next end step, each player may play the card they exiled this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new RoccoStreetChefEffect(), TargetController.YOU, false
        ));

        // Whenever a player plays a land from exile or casts a spell from exile, you put a +1/+1 counter on target creature and create a Food token.
        this.addAbility(new RoccoStreetChefTriggeredAbility());
    }

    private RoccoStreetChef(final RoccoStreetChef card) {
        super(card);
    }

    @Override
    public RoccoStreetChef copy() {
        return new RoccoStreetChef(this);
    }
}

class RoccoStreetChefEffect extends OneShotEffect {

    RoccoStreetChefEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles the top card of their library. " +
                "Until your next end step, each player may play the card they exiled this way";
    }

    private RoccoStreetChefEffect(final RoccoStreetChefEffect effect) {
        super(effect);
    }

    @Override
    public RoccoStreetChefEffect copy() {
        return new RoccoStreetChefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            player.moveCards(card, Zone.EXILED, source, game);
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.UntilYourNextEndStep,
                    false, playerId, null
            );
        }
        return true;
    }
}

class RoccoStreetChefTriggeredAbility extends TriggeredAbilityImpl {

    RoccoStreetChefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("you put a +1/+1 counter on target creature"));
        this.addEffect(new CreateTokenEffect(new FoodToken()).concatBy("and"));
        this.addTarget(new TargetCreaturePermanent());
        this.setTriggerPhrase("Whenever a player plays a land from exile or casts a spell from exile, ");
    }

    private RoccoStreetChefTriggeredAbility(final RoccoStreetChefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoccoStreetChefTriggeredAbility copy() {
        return new RoccoStreetChefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getZone() == Zone.EXILED;
    }
}
