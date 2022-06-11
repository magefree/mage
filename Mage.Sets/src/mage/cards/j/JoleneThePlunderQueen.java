package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Susucre
 */
public final class JoleneThePlunderQueen extends CardImpl {

    private static final FilterControlledPermanent filterTreasures = new FilterControlledPermanent(SubType.TREASURE, "treasures");

    public JoleneThePlunderQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player attacks one or more of your opponents, that attacking player creates a Treasure token.
        this.addAbility(new JoleneThePlunderQueenTriggeredAbility());

        // If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token.
        this.addAbility(new SimpleStaticAbility(new JoleneThePlunderQueenReplacementEffect()));

        // Sacrifice five Treasures: Put five +1/+1 counters on Jolene.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                new SacrificeTargetCost(new TargetControlledPermanent(5, filterTreasures))
        ));
    }

    private JoleneThePlunderQueen(final JoleneThePlunderQueen card) {
        super(card);
    }

    @Override
    public JoleneThePlunderQueen copy() {
        return new JoleneThePlunderQueen(this);
    }
}

// Based loosely on "Breena, the Demagogue" and "Mila, Crafty Companion"'s trigger abilities
class JoleneThePlunderQueenTriggeredAbility extends TriggeredAbilityImpl {

    JoleneThePlunderQueenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new JoleneThePlunderQueenCreateTreasureEffect(), false);
    }

    private JoleneThePlunderQueenTriggeredAbility(final JoleneThePlunderQueenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JoleneThePlunderQueenTriggeredAbility copy() {
        return new JoleneThePlunderQueenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Combat combat = game.getCombat();
        UUID joleneController = game.getControllerId(sourceId);
        Set<UUID> joleneOpponents = game.getOpponents(joleneController);

        // At most one trigger per combat.
        if(!combat.getAttackers()
                  .stream()
                  .anyMatch(attackerId -> {
                      // The trigger attempts to find at least one (attacker,defender)
                      //     for which the defender is one of jolene's controller opponent
                      UUID defenderId = combat.getDefenderId(attackerId);
                      return joleneOpponents.contains(defenderId);
                  })){
            return false;
        }

        getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks one of your opponents, " +
               "that attacking player creates a Treasure token.";
    }
}

class JoleneThePlunderQueenCreateTreasureEffect extends OneShotEffect {

    JoleneThePlunderQueenCreateTreasureEffect() {
        super(Outcome.Benefit);
        staticText = "that attacking player creates a Treasure token";
    }

    private JoleneThePlunderQueenCreateTreasureEffect(final JoleneThePlunderQueenCreateTreasureEffect effect) {
        super(effect);
    }

    @Override
    public JoleneThePlunderQueenCreateTreasureEffect copy() {
        return new JoleneThePlunderQueenCreateTreasureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)){
            new TreasureToken().putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}

// Identical to "Xorn"'s Replacement Effect
class JoleneThePlunderQueenReplacementEffect extends ReplacementEffectImpl {

    public JoleneThePlunderQueenReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token";
    }

    private JoleneThePlunderQueenReplacementEffect(final JoleneThePlunderQueenReplacementEffect effect) {
        super(effect);
    }

    @Override
    public JoleneThePlunderQueenReplacementEffect copy() {
        return new JoleneThePlunderQueenReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent && source.isControlledBy(event.getPlayerId())) {
            for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
                if (token.hasSubtype(SubType.TREASURE, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            TreasureToken treasureToken = null;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            for (Token token : tokens.keySet()) {
                if (token instanceof TreasureToken) {
                    treasureToken = (TreasureToken) token;
                    break;
                }
            }
            if (treasureToken == null) {
                treasureToken = new TreasureToken();
            }
            tokens.put(treasureToken, tokens.getOrDefault(treasureToken, 0) + 1);
        }
        return false;
    }
}
