package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.StanggTwinToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author awjackson
 */
public final class Stangg extends CardImpl {

    public Stangg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Stangg enters the battlefield, create Stangg Twin, a legendary 3/4 red and green Human Warrior creature token.
        // Exile that token when Stangg leaves the battlefield. Sacrifice Stangg when that token leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StanggCreateTokenEffect()));
    }

    private Stangg(final Stangg card) {
        super(card);
    }

    @Override
    public Stangg copy() {
        return new Stangg(this);
    }
}

class StanggCreateTokenEffect extends OneShotEffect {

    public StanggCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create Stangg Twin, a legendary 3/4 red and green Human Warrior creature token. "
                + "Exile that token when {this} leaves the battlefield. "
                + "Sacrifice {this} when that token leaves the battlefield";
    }

    public StanggCreateTokenEffect(final StanggCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new StanggTwinToken();
        token.putOntoBattlefield(1, game, source);
        game.addDelayedTriggeredAbility(new StanggLeavesTriggeredAbility(token, game), source);
        game.addDelayedTriggeredAbility(new StanggTwinLeavesTriggeredAbility(token), source);
        return true;
    }

    @Override
    public StanggCreateTokenEffect copy() {
        return new StanggCreateTokenEffect(this);
    }
}

class StanggLeavesTriggeredAbility extends DelayedTriggeredAbility {

    StanggLeavesTriggeredAbility(Token token, Game game) {
        super(new ExileTargetEffect());
        this.getEffects().setTargetPointer(new FixedTargets(token, game));
    }

    private StanggLeavesTriggeredAbility(final StanggLeavesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StanggLeavesTriggeredAbility copy() {
        return new StanggLeavesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
    }

    @Override
    public String getRule() {
        return "Exile that token when {this} leaves the battlefield.";
    }
}

class StanggTwinLeavesTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<UUID> tokenIds = new HashSet<>();

    StanggTwinLeavesTriggeredAbility(Token token) {
        super(new SacrificeSourceEffect(), Duration.Custom, false);
        tokenIds.addAll(token.getLastAddedTokenIds());
    }

    private StanggTwinLeavesTriggeredAbility(final StanggTwinLeavesTriggeredAbility ability) {
        super(ability);
        this.tokenIds.addAll(ability.tokenIds);
    }

    @Override
    public StanggTwinLeavesTriggeredAbility copy() {
        return new StanggTwinLeavesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (tokenIds.contains(event.getTargetId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            tokenIds.remove(event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        return tokenIds.isEmpty();
    }

    @Override
    public String getRule() {
        return "Sacrifice {this} when that token leaves the battlefield.";
    }
}
