package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author huangn, TheElk801
 */
public final class MeliraTheLivingCure extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public MeliraTheLivingCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If you would get one or more poison counters, instead you get one poison counter and you can't get additional poison counters this turn.
        this.addAbility(new SimpleStaticAbility(new MeliraTheLivingCureReplacementEffect()));

        // Exile Melira, the Living Cure: Choose another target creature or artifact. When it's put into a graveyard this turn, return that card to the battlefield under its owner's control.
        Ability ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(
                        new ReturnFromGraveyardToBattlefieldTargetEffect()
                                .setText("return that card to the battlefield under its owner's control"),
                        SetTargetPointer.CARD
                ).setTriggerPhrase("Choose another target creature or artifact. " +
                        "When it's put into a graveyard this turn, ")
        ), new ExileSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MeliraTheLivingCure(final MeliraTheLivingCure card) {
        super(card);
    }

    @Override
    public MeliraTheLivingCure copy() {
        return new MeliraTheLivingCure(this);
    }
}

class MeliraTheLivingCureReplacementEffect extends ReplacementEffectImpl {

    MeliraTheLivingCureReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would get one or more poison counters, instead you get " +
                "one poison counter and you can't get additional poison counters this turn";
    }

    private MeliraTheLivingCureReplacementEffect(final MeliraTheLivingCureReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(1);
        game.addEffect(new MeliraTheLivingCurePreventionEffect(), source);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId())
                && CounterType.POISON.getName().equals(event.getData())
                && event.getAmount() > 0;
    }

    @Override
    public MeliraTheLivingCureReplacementEffect copy() {
        return new MeliraTheLivingCureReplacementEffect(this);
    }
}

class MeliraTheLivingCurePreventionEffect extends ReplacementEffectImpl {

    MeliraTheLivingCurePreventionEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
    }

    private MeliraTheLivingCurePreventionEffect(final MeliraTheLivingCurePreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId())
                && CounterType.POISON.getName().equals(event.getData())
                && event.getAmount() > 0;
    }

    @Override
    public MeliraTheLivingCurePreventionEffect copy() {
        return new MeliraTheLivingCurePreventionEffect(this);
    }
}
