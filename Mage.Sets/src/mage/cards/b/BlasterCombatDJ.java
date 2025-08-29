package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ModularAbility;
import mage.constants.*;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class BlasterCombatDJ extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Other nontoken artifact creatures and Vehicles you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                Predicates.and(TokenPredicate.FALSE, CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()),
                SubType.VEHICLE.getPredicate())
        );
    }

    public BlasterCombatDJ(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BlasterMoraleBooster.class;

        // More Than Meets the Eye {1}{R}{G}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{R}{G}"));

        // Other nontoken artifact creatures and Vehicles you control have modular 1.
        Ability ability = new SimpleStaticAbility(new BlasterCombatDJReplacementEffect());
        ability.addEffect(new GainAbilityControlledEffect(
                new ModularAbility(1), Duration.WhileOnBattlefield, filter
        ).setText(""));
        this.addAbility(ability);

        // Whenever you put one or more +1/+1 counters on Blaster, convert it.
        this.addAbility(new BlasterCombatDJTriggeredAbility());
    }

    private BlasterCombatDJ(final BlasterCombatDJ card) {
        super(card);
    }

    @Override
    public BlasterCombatDJ copy() {
        return new BlasterCombatDJ(this);
    }
}

class BlasterCombatDJReplacementEffect extends ReplacementEffectImpl {

    BlasterCombatDJReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Other nontoken artifact creatures and Vehicles you control have modular 1. " +
                "<i>(They enter with an additional +1/+1 counter on them. When they die, you may put " +
                "their +1/+1 counters on target artifact creature.)</i>";
    }

    private BlasterCombatDJReplacementEffect(BlasterCombatDJReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creatureOrVehicle = ((EntersTheBattlefieldEvent) event).getTarget();
        return creatureOrVehicle != null
                && creatureOrVehicle.getId() != source.getSourceId()
                && creatureOrVehicle.isControlledBy(source.getControllerId())
                && ((creatureOrVehicle.isCreature(game)
                      && creatureOrVehicle.isArtifact(game)
                      && !(creatureOrVehicle instanceof PermanentToken))
                    || creatureOrVehicle.hasSubtype(SubType.VEHICLE, game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player player = game.getPlayer(source.getControllerId());
        if (creature == null || player == null) {
            return false;
        }
        creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        return false;
    }

    @Override
    public BlasterCombatDJReplacementEffect copy() {
        return new BlasterCombatDJReplacementEffect(this);
    }
}

class BlasterCombatDJTriggeredAbility extends TriggeredAbilityImpl {

    BlasterCombatDJTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect().setText("convert {this}"));
        this.setTriggerPhrase("Whenever you put one or more +1/+1 counters on {this}, ");
    }

    private BlasterCombatDJTriggeredAbility(final BlasterCombatDJTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlasterCombatDJTriggeredAbility copy() {
        return new BlasterCombatDJTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.P1P1.getName())
                && isControlledBy(event.getPlayerId())
                && event.getTargetId().equals(getSourceId());
    }

}
