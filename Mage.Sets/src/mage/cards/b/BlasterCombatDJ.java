package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ModularAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Grath, DarioBarrabinoDev
 */
public final class BlasterCombatDJ extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent("other nontoken artifact creatures and Vehicles you control");
    private static final FilterPermanent filterArtifact = new FilterArtifactPermanent("another target artifact");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, ComparisonType.EQUAL_TO, 0);

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(
                Predicates.and(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()),
                SubType.VEHICLE.getPredicate()
        ));
        filterArtifact.add(AnotherPredicate.instance);
    }

    public BlasterCombatDJ(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{3}{R}{G}",
                "Blaster, Morale Booster",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "RG");

        // Blaster, Combat DJ
        this.getLeftHalfCard().setPT(3, 3);

        // More Than Meets the Eye {1}{R}{G}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{R}{G}"));

        // Other nontoken artifact creatures and Vehicles you control have modular 1.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ModularAbility(null, 1), Duration.WhileOnBattlefield, filter
        ));
        // A granted modular can't give counters to the permanent that is entering, so that part is a replacement effect
        ability.addEffect(new BlasterCombatDJReplacementEffect(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Whenever you put one or more +1/+1 counters on Blaster, convert it.
        this.getLeftHalfCard().addAbility(new BlasterCombatDJTriggeredAbility());

        // Blaster, Morale Booster
        // Modular 3
        this.getRightHalfCard().addAbility(new ModularAbility(null, 3));

        // {X}, {T}: Move X +1/+1 counters from Blaster onto another target artifact. That artifact gains haste until end of turn. If Blaster has no +1/+1 counters on it, convert it. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new BlasterMoraleBoosterEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("that artifact gains haste until end of turn"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "if {this} has no +1/+1 counters on it, convert it"
        ));
        ability.addTarget(new TargetPermanent(filterArtifact));
        this.getRightHalfCard().addAbility(ability);
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

    private final FilterPermanent filter;

    BlasterCombatDJReplacementEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        this.filter = filter;
        staticText = "<i>(They enter with an additional +1/+1 counter on them. "
                + "When they die, you may put their +1/+1 counters on target artifact creature.)</i>";
    }

    private BlasterCombatDJReplacementEffect(final BlasterCombatDJReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public BlasterCombatDJReplacementEffect copy() {
        return new BlasterCombatDJReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }
}

class BlasterCombatDJTriggeredAbility extends TriggeredAbilityImpl {

    BlasterCombatDJTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect().setText("convert it"));
        setTriggerPhrase("Whenever you put one or more +1/+1 counters on {this}, ");
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
                && event.getAmount() > 0
                && isControlledBy(event.getPlayerId())
                && event.getTargetId().equals(getSourceId());
    }
}

class BlasterMoraleBoosterEffect extends OneShotEffect {

    BlasterMoraleBoosterEffect() {
        super(Outcome.Benefit);
        staticText = "move X +1/+1 counters from {this} onto another target artifact";
    }

    private BlasterMoraleBoosterEffect(final BlasterMoraleBoosterEffect effect) {
        super(effect);
    }

    @Override
    public BlasterMoraleBoosterEffect copy() {
        return new BlasterMoraleBoosterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || targetPermanent == null) {
            return false;
        }
        // If Blaster has fewer than X counters, all of them are moved
        int amount = Math.min(
                CardUtil.getSourceCostsTag(game, source, "X", 0),
                sourcePermanent.getCounters(game).getCount(CounterType.P1P1)
        );
        if (amount < 1) {
            return false;
        }
        sourcePermanent.removeCounters(CounterType.P1P1.createInstance(amount), source, game);
        targetPermanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
        return true;
    }
}
