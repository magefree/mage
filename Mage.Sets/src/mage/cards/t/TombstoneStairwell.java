package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TombspawnZombieToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class TombstoneStairwell extends CardImpl {

    public TombstoneStairwell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.supertype.add(SuperType.WORLD);

        // Cumulative upkeep-Pay {1}{B}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}{B}")));

        // At the beginning of each upkeep, if Tombstone Stairwell is on the battlefield, each player creates a 2/2 black Zombie creature token with haste named Tombspawn for each creature card in their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TombstoneStairwellCreateTokenEffect(), TargetController.ANY, false));

        // At the beginning of each end step or when Tombstone Stairwell leaves the battlefield, destroy all tokens created with Tombstone Stairwell. They can't be regenerated.
        this.addAbility(new TombstoneStairwellTriggeredAbility());
    }

    private TombstoneStairwell(final TombstoneStairwell card) {
        super(card);
    }

    @Override
    public TombstoneStairwell copy() {
        return new TombstoneStairwell(this);
    }
}

class TombstoneStairwellCreateTokenEffect extends OneShotEffect {

    TombstoneStairwellCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "if {this} is on the battlefield, each player creates a 2/2 black Zombie creature token with haste named Tombspawn for each creature card in their graveyard";
    }

    TombstoneStairwellCreateTokenEffect(final TombstoneStairwellCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public TombstoneStairwellCreateTokenEffect copy() {
        return new TombstoneStairwellCreateTokenEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Token token = new TombspawnZombieToken();
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (game.getPlayer(source.getControllerId()) != null && activePlayer != null && permanent != null) {
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                int creatureCardsInGraveyard = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, source.getSourceId(), source, game);
                token.putOntoBattlefield(creatureCardsInGraveyard, game, source, playerId);
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    tokensCreated.add(tokenId);
                }
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
            return true;
        }
        return false;
    }
}

class TombstoneStairwellTriggeredAbility extends TriggeredAbilityImpl {

    TombstoneStairwellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TombstoneStairwellDestroyEffect(), false);
    }

    TombstoneStairwellTriggeredAbility(TombstoneStairwellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(sourceId);
            if (permanent != null) {
                for (Effect effect : this.getEffects()) {
                    if (effect instanceof TombstoneStairwellDestroyEffect) {
                        ((TombstoneStairwellDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, false));
                    }
                    if (getTargets().isEmpty()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            }
        } else if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (event.getTargetId().equals(this.getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                    for (Effect effect : this.getEffects()) {
                        if (effect instanceof TombstoneStairwellDestroyEffect) {
                            ((TombstoneStairwellDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, true));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TombstoneStairwellTriggeredAbility copy() {
        return new TombstoneStairwellTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step or when {this} leaves the battlefield, destroy all tokens created with {this}. They can't be regenerated.";
    }
}

class TombstoneStairwellDestroyEffect extends OneShotEffect {

    private String cardZoneString;

    TombstoneStairwellDestroyEffect() {
        super(Outcome.Benefit);
    }

    TombstoneStairwellDestroyEffect(final TombstoneStairwellDestroyEffect effect) {
        super(effect);
        this.cardZoneString = effect.cardZoneString;
    }

    @Override
    public TombstoneStairwellDestroyEffect copy() {
        return new TombstoneStairwellDestroyEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(cardZoneString);
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source, game, true);
                }
            }
        }
        return true;
    }

    public void setCardZoneString(String cardZoneString) {
        this.cardZoneString = cardZoneString;
    }
}
