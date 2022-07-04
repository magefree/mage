package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ReplaceTreasureWithAdditionalEffect;
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
        this.addAbility(new SimpleStaticAbility(new ReplaceTreasureWithAdditionalEffect()));

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
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new TreasureToken(), StaticValue.get(1)), false);
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
        if(combat.getAttackers()
                  .stream()
                  .noneMatch(attackerId -> {
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