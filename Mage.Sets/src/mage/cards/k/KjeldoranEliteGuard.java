package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class KjeldoranEliteGuard extends CardImpl {

    public KjeldoranEliteGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Target creature gets +2/+2 until end of turn.
        // When that creature leaves the battlefield this turn, sacrifice Kjeldoran Elite Guard.
        // Activate only during combat.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new KjeldoranEliteGuardEffect(),
                new TapSourceCost(),
                new IsPhaseCondition(TurnPhase.COMBAT, false));
        ability.addTarget(new TargetCreaturePermanent());

        this.addAbility(ability);
    }

    private KjeldoranEliteGuard(final KjeldoranEliteGuard card) { super(card); }

    @Override
    public Card copy() {
        return new KjeldoranEliteGuard(this);
    }
}

class KjeldoranEliteGuardEffect extends OneShotEffect {

    KjeldoranEliteGuardEffect() {
        super(Outcome.Neutral);
        staticText = "Target creature gets +2/+2 until end of turn. "
                + "When that creature leaves the battlefield this turn, sacrifice Kjeldoran Elite Guard. "
                + "Activate only during combat.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanent(source.getFirstTarget()) == null) { return false; }

        // Target creature gets +2/+2 until end of turn.
        BoostTargetEffect buffEffect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        buffEffect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        game.addEffect(buffEffect, source);

        // When that creature leaves the battlefield this turn, sacrifice Kjeldoran Elite Guard.
        game.addDelayedTriggeredAbility(
                new KjeldoranEliteGuardDelayedTriggeredAbility(source.getFirstTarget()),
                source);

        return true;
    }

    private KjeldoranEliteGuardEffect(KjeldoranEliteGuardEffect effect) { super(effect); }

    @Override
    public KjeldoranEliteGuardEffect copy() { return new KjeldoranEliteGuardEffect(this); }
}

class KjeldoranEliteGuardDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID creatureId;

    KjeldoranEliteGuardDelayedTriggeredAbility(UUID creatureId) {
        super(new SacrificeSourceEffect(), Duration.EndOfTurn, true);
        this.creatureId = creatureId;
    }

    KjeldoranEliteGuardDelayedTriggeredAbility(KjeldoranEliteGuardDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureId = ability.creatureId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) { return event.getTargetId().equals(creatureId); }

    @Override
    public KjeldoranEliteGuardDelayedTriggeredAbility copy() {
        return new KjeldoranEliteGuardDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() { return "that creature left the battlefield this turn"; }
}