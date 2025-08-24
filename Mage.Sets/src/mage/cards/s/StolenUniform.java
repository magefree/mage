package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class StolenUniform extends CardImpl {

    public StolenUniform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose target creature you control and target Equipment. Gain control of that Equipment until end of turn. Attach it to the chosen creature. When you lose control of that Equipment this turn, if it's attached to a creature you control, unattach it.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                .setText("choose target creature you control and target Equipment. " +
                        "Gain control of that Equipment until end of turn")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addEffect(new StolenUniformAttachEffect());
        this.getSpellAbility().addEffect(new StolenUniformTriggerEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
    }

    private StolenUniform(final StolenUniform card) {
        super(card);
    }

    @Override
    public StolenUniform copy() {
        return new StolenUniform(this);
    }
}

class StolenUniformAttachEffect extends OneShotEffect {

    StolenUniformAttachEffect() {
        super(Outcome.Benefit);
        staticText = "Attach it to the chosen creature";
        this.setTargetPointer(new EachTargetPointer());
    }

    private StolenUniformAttachEffect(final StolenUniformAttachEffect effect) {
        super(effect);
    }

    @Override
    public StolenUniformAttachEffect copy() {
        return new StolenUniformAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return permanents.size() >= 2 && permanents.get(0).addAttachment(permanents.get(1).getId(), source, game);
    }
}

class StolenUniformTriggerEffect extends OneShotEffect {

    StolenUniformTriggerEffect() {
        super(Outcome.Benefit);
        staticText = "When you lose control of that Equipment this turn, " +
                "if it's attached to a creature you control, unattach it";
        this.setTargetPointer(new SecondTargetPointer());
    }

    private StolenUniformTriggerEffect(final StolenUniformTriggerEffect effect) {
        super(effect);
    }

    @Override
    public StolenUniformTriggerEffect copy() {
        return new StolenUniformTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new StolenUniformTriggeredAbility(permanent, game), source);
        return true;
    }
}

class StolenUniformTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;
    private final int turnNum;

    StolenUniformTriggeredAbility(Permanent permanent, Game game) {
        super(new StolenUniformUnattachEffect(permanent, game), Duration.Custom, false, false);
        setTriggerPhrase("When you lose control of that Equipment this turn, ");
        this.mor = new MageObjectReference(permanent, game);
        this.turnNum = game.getTurnNum();
    }

    private StolenUniformTriggeredAbility(final StolenUniformTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
        this.turnNum = ability.turnNum;
    }

    @Override
    public StolenUniformTriggeredAbility copy() {
        return new StolenUniformTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // We can't use end of turn duration as losing control happens after the trigger has gone away, so we need this workaround
        if (game.getTurnNum() > this.turnNum) {
            this.setDuration(Duration.EndOfTurn);
            return false;
        }
        return isControlledBy(event.getPlayerId())
                && mor.zoneCounterIsCurrent(game)
                && event.getTargetId().equals(mor.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(mor.getPermanent(game))
                .map(Permanent::getAttachedTo)
                .map(game::getControllerId)
                .map(this::isControlledBy)
                .orElse(false);
    }
}

class StolenUniformUnattachEffect extends OneShotEffect {

    StolenUniformUnattachEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        staticText = "unattach it";
        this.setTargetPointer(new FixedTarget(permanent, game));
    }

    private StolenUniformUnattachEffect(final StolenUniformUnattachEffect effect) {
        super(effect);
    }

    @Override
    public StolenUniformUnattachEffect copy() {
        return new StolenUniformUnattachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.unattach(game));
        return true;
    }
}
