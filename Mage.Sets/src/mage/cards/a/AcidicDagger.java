package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class AcidicDagger extends CardImpl {

    public AcidicDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {4}, {tap}: Whenever target creature deals combat damage to a non-Wall creature this turn,
        // destroy that non-Wall creature. When the targeted creature leaves the battlefield this turn,
        // sacrifice Acidic Dagger. Activate this ability only before blockers are declared.
        Ability ability = new ConditionalActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new AcidicDaggerDestroyNonWallAbility()),
                new GenericManaCost(4),
                BeforeBlockersAreDeclaredCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AcidicDaggerSacrificeSourceAbility()));

        this.addAbility(ability);
    }

    private AcidicDagger(final AcidicDagger card) {
        super(card);
    }

    @Override
    public AcidicDagger copy() {
        return new AcidicDagger(this);
    }
}

class AcidicDaggerDestroyNonWallAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    AcidicDaggerDestroyNonWallAbility() {
        super(new DestroyTargetEffect(), Duration.EndOfTurn, false);
    }

    protected AcidicDaggerDestroyNonWallAbility(AcidicDaggerDestroyNonWallAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent damagedPermanent = game.getPermanent(event.getTargetId());
        Permanent attackingPermanent = game.getPermanent(event.getSourceId());
        Permanent targetedPermanent = game.getPermanent(this.getTargets().getFirstTarget());

        if (damagedPermanent == null
                || attackingPermanent == null
                || targetedPermanent == null
                || !filter.match(damagedPermanent, game)
                || !((DamagedEvent) event).isCombatDamage()
                || !attackingPermanent.getId().equals(targetedPermanent.getId())) {
            return false;
        }

        this.getTargets().clear(); // else ability fizzles if target creature died
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(damagedPermanent, game));
        }
        return true;
    }

    @Override
    public AcidicDaggerDestroyNonWallAbility copy() {
        return new AcidicDaggerDestroyNonWallAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever target creature deals combat damage to a non-Wall creature this turn, destroy that non-Wall creature.";
    }
}

// Based on HeartWolfDelayedTriggeredAbility
class AcidicDaggerSacrificeSourceAbility extends DelayedTriggeredAbility {

    AcidicDaggerSacrificeSourceAbility() {
        super(new SacrificeSourceEffect(), Duration.EndOfTurn, false);
    }

    protected AcidicDaggerSacrificeSourceAbility(AcidicDaggerSacrificeSourceAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getTarget() != null && zEvent.getTargetId().equals(getTargets().getFirstTarget())) {
            this.getTargets().clear(); // else ability fizzles because target creature died
            return true;
        }
        return false;
    }

    @Override
    public AcidicDaggerSacrificeSourceAbility copy() {
        return new AcidicDaggerSacrificeSourceAbility(this);
    }

    @Override
    public String getRule() {
        return "When the targeted creature leaves the battlefield this turn, sacrifice {this}.";
    }
}
