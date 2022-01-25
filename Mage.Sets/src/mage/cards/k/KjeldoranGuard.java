package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class KjeldoranGuard extends CardImpl {

    private static final FilterLandPermanent snowLandFiler = new FilterLandPermanent("a snow land");

    static { snowLandFiler.add(SuperType.SNOW.getPredicate()); }

    public KjeldoranGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Target creature gets +1/+1 until end of turn.
        // When that creature leaves the battlefield this turn, sacrifice Kjeldoran Guard.
        // Activate only during combat and only if defending player controls no snow lands.
        CompoundCondition snowLandAndCombatCondition = new CompoundCondition(
                new InvertCondition(new OpponentControlsPermanentCondition(snowLandFiler)),
                new IsPhaseCondition(TurnPhase.COMBAT, false));

        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new KjeldoranGuardEffect(),
                new TapSourceCost(),
                snowLandAndCombatCondition);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private KjeldoranGuard(final KjeldoranGuard card) { super(card); }

    @Override
    public Card copy() {
        return new KjeldoranGuard(this);
    }
}

class KjeldoranGuardEffect extends OneShotEffect {

    KjeldoranGuardEffect() {
        super(Outcome.Neutral);
        staticText = "Target creature gets +1/+1 until end of turn." +
                "When that creature leaves the battlefield this turn, sacrifice Kjeldoran Guard." +
                "Activate only during combat and only if defending player controls no snow lands.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null) { return false; }

        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature == null) { return false; }

        // Target creature gets +2/+2 until end of turn.
        BoostTargetEffect buffEffect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        buffEffect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        game.addEffect(buffEffect, source);

        // When that creature leaves the battlefield this turn, sacrifice Kjeldoran  Guard.
        SacrificeTargetEffect sacrificeKjeldoranGuardEffect = new SacrificeTargetEffect();
        sacrificeKjeldoranGuardEffect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        game.addDelayedTriggeredAbility(
                new KjeldoranGuardDelayedTriggeredAbility(
                        sacrificeKjeldoranGuardEffect,
                        source.getFirstTarget()),
                source);

        return true;
    }

    private KjeldoranGuardEffect(KjeldoranGuardEffect effect) { super(effect); }

    @Override
    public KjeldoranGuardEffect copy() { return new KjeldoranGuardEffect(this); }
}

class KjeldoranGuardDelayedTriggeredAbility extends DelayedTriggeredAbility {

    UUID creatureId;

    KjeldoranGuardDelayedTriggeredAbility(Effect effect, UUID creatureId) {
        super(effect, Duration.EndOfTurn, true);
        this.creatureId = creatureId;
    }

    KjeldoranGuardDelayedTriggeredAbility(KjeldoranGuardDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureId = ability.creatureId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(creatureId);
    }

    @Override
    public KjeldoranGuardDelayedTriggeredAbility copy() {
        return new KjeldoranGuardDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() { return "that creature left the battlefield this turn"; }
}