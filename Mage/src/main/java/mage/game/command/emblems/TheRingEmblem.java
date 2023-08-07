package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.TemptedByTheRingWatcher;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRingEmblem extends Emblem {

    private static final FilterPermanent filter = new FilterControlledPermanent("your Ring-bearer");

    static {
        filter.add(TheRingEmblemPredicate.instance);
    }

    public TheRingEmblem(UUID controllerId) {
        super("The Ring");
        this.setControllerId(controllerId);
    }

    private TheRingEmblem(final TheRingEmblem card) {
        super(card);
    }

    @Override
    public TheRingEmblem copy() {
        return new TheRingEmblem(this);
    }

    public void addNextAbility(Game game) {
        String logText;
        Ability ability;
        switch (TemptedByTheRingWatcher.getCount(this.getControllerId(), game)) {
            case 0:
                logText = "Your Ring-bearer is legendary and can't be blocked by creatures with greater power.";
                ability = new SimpleStaticAbility(Zone.COMMAND, new TheRingEmblemLegendaryEffect());
                ability.addEffect(new TheRingEmblemEvasionEffect());
                break;
            case 1:
                logText = "Whenever your Ring-bearer attacks, draw a card, then discard a card.";
                ability = new AttacksCreatureYouControlTriggeredAbility(
                        Zone.COMMAND,
                        new DrawDiscardControllerEffect(1, 1),
                        false, filter, false
                ).setTriggerPhrase("Whenever your Ring-bearer attacks, ");
                break;
            case 2:
                logText ="Whenever your Ring-bearer becomes blocked by a creature, that creature's controller sacrifices it at end of combat.";
                ability = new TheRingEmblemTriggeredAbility();
                break;
            case 3:
                logText = "Whenever your Ring-bearer deals combat damage to a player, each opponent loses 3 life.";
                ability = new DealsDamageToAPlayerAllTriggeredAbility(
                        Zone.COMMAND, new LoseLifeOpponentsEffect(3), filter, false,
                        SetTargetPointer.NONE, true, false
                );
                break;
            default:
                return;
        }
        UUID controllerId = this.getControllerId();
        this.getAbilities().add(ability);
        ability.setSourceId(this.getId());
        ability.setControllerId(controllerId);
        game.getState().addAbility(ability, this);

        String name = "";
        if(controllerId  != null){
            Player player = game.getPlayer(controllerId);
            if(player != null){
                name = player.getLogName();
            }
        }
        game.informPlayers(name + " gains a new Ring ability: \"" + logText + "\"");
    }
}

enum TheRingEmblemPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isRingBearer();
    }
}

class TheRingEmblemLegendaryEffect extends ContinuousEffectImpl {

    TheRingEmblemLegendaryEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "your Ring-bearer is legendary";
    }

    private TheRingEmblemLegendaryEffect(final TheRingEmblemLegendaryEffect effect) {
        super(effect);
    }

    @Override
    public TheRingEmblemLegendaryEffect copy() {
        return new TheRingEmblemLegendaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .filter(Objects::nonNull)
                .map(player -> player.getRingBearer(game))
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        permanent.addSuperType(game, SuperType.LEGENDARY);
        return true;
    }
}

class TheRingEmblemEvasionEffect extends RestrictionEffect {

    TheRingEmblemEvasionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "and can't be blocked by creatures with greater power";
    }

    private TheRingEmblemEvasionEffect(final TheRingEmblemEvasionEffect effect) {
        super(effect);
    }

    @Override
    public TheRingEmblemEvasionEffect copy() {
        return new TheRingEmblemEvasionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(source.getControllerId())
                && permanent.isRingBearer();
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() <= attacker.getPower().getValue();
    }
}

class TheRingEmblemTriggeredAbility extends TriggeredAbilityImpl {

    TheRingEmblemTriggeredAbility() {
        super(Zone.COMMAND, new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeTargetEffect())));
    }

    private TheRingEmblemTriggeredAbility(final TheRingEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRingEmblemTriggeredAbility copy() {
        return new TheRingEmblemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getTargetId());
        Permanent blocker = game.getPermanent(event.getSourceId());
        if (attacker == null
                || blocker == null
                || attacker.isControlledBy(getControllerId())
                || !attacker.isRingBearer()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(blocker, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever your Ring-bearer becomes blocked by a creature, " +
                "that creature's controller sacrifices it at end of combat.";
    }
}
