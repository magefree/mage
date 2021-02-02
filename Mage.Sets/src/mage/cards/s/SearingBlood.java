package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SearingBlood extends CardImpl {

    public SearingBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Searing Blood deals 2 damage to target creature. When that creature dies this turn, Searing Blood deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new SearingBloodEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SearingBlood(final SearingBlood card) {
        super(card);
    }

    @Override
    public SearingBlood copy() {
        return new SearingBlood(this);
    }
}

class SearingBloodEffect extends OneShotEffect {

    public SearingBloodEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature. When that creature dies this turn, {this} deals 3 damage to that creature's controller";
    }

    public SearingBloodEffect(final SearingBloodEffect effect) {
        super(effect);
    }

    @Override
    public SearingBloodEffect copy() {
        return new SearingBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SearingBloodDelayedTriggeredAbility(source.getFirstTarget());
        game.addDelayedTriggeredAbility(delayedAbility, source);

        return new DamageTargetEffect(2).apply(game, source);
    }
}

class SearingBloodDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID target;

    public SearingBloodDelayedTriggeredAbility(UUID target) {
        super(new SearingBloodDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public SearingBloodDelayedTriggeredAbility(SearingBloodDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return zEvent.isDiesEvent();
        }
        return false;
    }

    @Override
    public SearingBloodDelayedTriggeredAbility copy() {
        return new SearingBloodDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, {this} deals 3 damage to that creature's controller.";
    }
}

class SearingBloodDelayedEffect extends OneShotEffect {

    protected UUID target;

    public SearingBloodDelayedEffect(UUID target) {
        super(Outcome.Damage);
        this.target = target;
    }

    public SearingBloodDelayedEffect(final SearingBloodDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public SearingBloodDelayedEffect copy() {
        return new SearingBloodDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                MageObject sourceObject = source.getSourceObject(game);
                player.damage(3, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
