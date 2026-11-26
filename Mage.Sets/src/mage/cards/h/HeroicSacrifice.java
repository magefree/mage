package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Alexsandr0x
 */
public final class HeroicSacrifice extends CardImpl {

    public HeroicSacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose target creature you control. Until end of turn, all damage that would be dealt
        // to you and creatures you control is dealt to the chosen creature instead (if it's still
        // on the battlefield). When that creature dies this turn, put its counters on up to one
        // target creature you control and draw a card.
        this.getSpellAbility().addEffect(new HeroicSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private HeroicSacrifice(final HeroicSacrifice card) {
        super(card);
    }

    @Override
    public HeroicSacrifice copy() {
        return new HeroicSacrifice(this);
    }
}

class HeroicSacrificeEffect extends OneShotEffect {

    HeroicSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature you control. Until end of turn, all damage that would be dealt to you and creatures you control is dealt to the chosen creature instead (if it's still on the battlefield). When that creature dies this turn, put its counters on up to one target creature you control and draw a card.";
    }

    private HeroicSacrificeEffect(final HeroicSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public HeroicSacrificeEffect copy() {
        return new HeroicSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(creature, game);
        game.addEffect(new HeroicSacrificeRedirectionEffect(mor), source);
        game.addDelayedTriggeredAbility(new HeroicSacrificeDelayedTriggeredAbility(mor), source);
        return true;
    }
}

class HeroicSacrificeRedirectionEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    HeroicSacrificeRedirectionEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        this.mor = mor;
    }

    private HeroicSacrificeRedirectionEffect(final HeroicSacrificeRedirectionEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public HeroicSacrificeRedirectionEffect copy() {
        return new HeroicSacrificeRedirectionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            return permanent != null
                    && permanent.isCreature(game)
                    && permanent.isControlledBy(source.getControllerId())
                    && !permanent.getId().equals(mor.getSourceId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent creature = mor.getPermanent(game);
        if (creature == null) {
            // 614.9: if the chosen creature left the battlefield, the damage isn't redirected
            return false;
        }
        Permanent damagedPermanent = game.getPermanent(event.getTargetId());
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        String damagedName = damagedPermanent != null ? damagedPermanent.getLogName()
                : damagedPlayer != null ? damagedPlayer.getLogName() : "unknown";
        game.informPlayers(creature.getLogName() + ": gets " + damageEvent.getAmount()
                + " damage redirected from " + damagedName);
        creature.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game,
                damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
        return true;
    }
}

class HeroicSacrificeDelayedTriggeredAbility extends WhenTargetDiesDelayedTriggeredAbility {

    HeroicSacrificeDelayedTriggeredAbility(MageObjectReference mor) {
        super(new HeroicSacrificeDiesEffect(), SetTargetPointer.NONE);
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.mor = mor;
    }

    private HeroicSacrificeDelayedTriggeredAbility(final HeroicSacrificeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeroicSacrificeDelayedTriggeredAbility copy() {
        return new HeroicSacrificeDelayedTriggeredAbility(this);
    }

    @Override
    public void init(Game game) {
        // the creature is set from the spell's target; keep this ability's own "up to one target"
    }
}

class HeroicSacrificeDiesEffect extends OneShotEffect {

    HeroicSacrificeDiesEffect() {
        super(Outcome.Benefit);
        staticText = "put its counters on up to one target creature you control";
    }

    private HeroicSacrificeDiesEffect(final HeroicSacrificeDiesEffect effect) {
        super(effect);
    }

    @Override
    public HeroicSacrificeDiesEffect copy() {
        return new HeroicSacrificeDiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent deadCreature = (Permanent) getValue("creatureDied");
        Permanent recipient = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (deadCreature == null || recipient == null) {
            return false;
        }
        for (Counter counter : deadCreature.getCounters(game).values()) {
            recipient.addCounters(counter, source.getControllerId(), source, game);
        }
        return true;
    }
}
