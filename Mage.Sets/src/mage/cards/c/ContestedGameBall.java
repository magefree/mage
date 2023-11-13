package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ContestedGameBall extends CardImpl {

    public ContestedGameBall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you're dealt combat damage, the attacking player gains control of Contested Game Ball and untaps it.
        this.addAbility(new ContestedGameBallTriggeredAbility());

        // {2}, {T}: Draw a card and put a point counter on Contested Game Ball.
        // Then if it has five or more point counters on it, sacrifice it and create a Treasure token.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.POINT.createInstance()).concatBy("and"));
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(),
                new SourceHasCounterCondition(CounterType.POINT, 5),
                "Then if it has five or more point counters on it, sacrifice it and create a Treasure token")
                .addEffect(new CreateTokenEffect(new TreasureToken()))
        );
        this.addAbility(ability);

    }

    private ContestedGameBall(final ContestedGameBall card) {
        super(card);
    }

    @Override
    public ContestedGameBall copy() {
        return new ContestedGameBall(this);
    }
}

class ContestedGameBallTriggeredAbility extends TriggeredAbilityImpl {

    ContestedGameBallTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ContestedGameBallEffect());
        setTriggerPhrase("Whenever you're dealt combat damage, ");
    }

    private ContestedGameBallTriggeredAbility(final ContestedGameBallTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedBatchEvent) event).isCombatDamage() && event.getPlayerId().equals(this.getControllerId())) {
            this.getAllEffects().setTargetPointer(new FixedTarget(game.getActivePlayerId()));
            // attacking player is active player
            return true;
        }
        return false;
    }

    @Override
    public ContestedGameBallTriggeredAbility copy() {
        return new ContestedGameBallTriggeredAbility(this);
    }

}

class ContestedGameBallEffect extends OneShotEffect {

    ContestedGameBallEffect() {
        super(Outcome.GainControl);
        this.staticText = "the attacking player gains control of {this} and untaps it";
    }

    private ContestedGameBallEffect(final ContestedGameBallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || newController == null || controller.getId().equals(newController.getId())) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        game.addEffect(effect, source);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            sourcePermanent.untap(game);
        }
        return true;
    }

    @Override
    public ContestedGameBallEffect copy() {
        return new ContestedGameBallEffect(this);
    }
}
