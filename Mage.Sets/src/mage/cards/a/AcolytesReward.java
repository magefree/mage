package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AcolytesReward extends CardImpl {

    public AcolytesReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, Acolyte's Reward deals that much damage to any target.
        this.getSpellAbility().addEffect(new AcolytesRewardEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addHint(DevotionCount.W.getHint());
    }

    private AcolytesReward(final AcolytesReward card) {
        super(card);
    }

    @Override
    public AcolytesReward copy() {
        return new AcolytesReward(this);
    }
}

class AcolytesRewardEffect extends PreventionEffectImpl {

    protected int amount = 0;

    AcolytesRewardEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, {this} deals that much damage to any target";
    }

    private AcolytesRewardEffect(final AcolytesRewardEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AcolytesRewardEffect copy() {
        return new AcolytesRewardEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amount = DevotionCount.W.calculate(game, source, this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        int toPrevent = amount;
        if (event.getAmount() < this.amount) {
            toPrevent = event.getAmount();
            amount -= event.getAmount();
        } else {
            amount = 0;
        }
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent, ((DamageEvent) event).isCombatDamage());
        if (game.replaceEvent(preventEvent)) {
            return result;
        }
        Permanent targetCreature = game.getPermanent(event.getTargetId());
        if (targetCreature == null) {
            return result;
        }
        if (amount == 0) {
            this.used = true;
            this.discard();
        }
        if (event.getAmount() >= toPrevent) {
            event.setAmount(event.getAmount() - toPrevent);
        } else {
            event.setAmount(0);
            result = true;
        }
        if (toPrevent == 0) {
            return result;
        }
        game.informPlayers("Acolyte's Reward prevented " + toPrevent + " to " + targetCreature.getName());
        game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent));

        Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.damage(toPrevent, source.getSourceId(), source, game);
            game.informPlayers("Acolyte's Reward deals " + toPrevent + " damage to " + targetPlayer.getLogName());
            return result;
        }
        Permanent targetDamageCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetDamageCreature == null) {
            return result;
        }
        targetDamageCreature.damage(toPrevent, source.getSourceId(), source, game, false, true);
        game.informPlayers("Acolyte's Reward deals " + toPrevent + " damage to " + targetDamageCreature.getName());
        return result;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getFirstTarget());
    }

}
