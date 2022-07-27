package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class DelifsCone extends CardImpl {

    public DelifsCone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {tap}, Sacrifice Delif's Cone: This turn, when target creature you control attacks and isn't blocked, you may gain life equal to its power. If you do, it assigns no combat damage this turn.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new DelifsConeTriggeredAbility()), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DelifsCone(final DelifsCone card) {
        super(card);
    }

    @Override
    public DelifsCone copy() {
        return new DelifsCone(this);
    }
}

class DelifsConeTriggeredAbility extends DelayedTriggeredAbility {

    DelifsConeTriggeredAbility() {
        super(new DelifsConeLifeEffect(), Duration.EndOfTurn, false, true);
        this.addEffect(new AssignNoCombatDamageTargetEffect());
    }

    private DelifsConeTriggeredAbility(final DelifsConeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getFirstTarget());
    }

    @Override
    public DelifsConeTriggeredAbility copy() {
        return new DelifsConeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "This turn, when target creature you control attacks and isn't blocked, " +
                "you may gain life equal to its power. If you do, it assigns no combat damage this turn.";
    }
}

class DelifsConeLifeEffect extends OneShotEffect {

    DelifsConeLifeEffect() {
        super(Outcome.Benefit);
    }

    private DelifsConeLifeEffect(final DelifsConeLifeEffect effect) {
        super(effect);
    }

    @Override
    public DelifsConeLifeEffect copy() {
        return new DelifsConeLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (player != null || permanent != null) {
            player.gainLife(permanent.getPower().getValue(), game, source);
            return true;
        }
        return false;
    }
}
