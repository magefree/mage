package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
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


    AcolytesRewardEffect() {
        super(Duration.EndOfTurn, 0, false, true, DevotionCount.W);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, {this} deals that much damage to any target";
    }

    private AcolytesRewardEffect(final AcolytesRewardEffect effect) {
        super(effect);
    }

    @Override
    public AcolytesRewardEffect copy() {
        return new AcolytesRewardEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        if (preventionData.getPreventedDamage() > 0) {
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game, false, true);
            }
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetCreature != null) {
                targetPlayer.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game, false, true);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getFirstTarget());
    }

}
