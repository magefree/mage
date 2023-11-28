
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class DictateOfTheTwinGods extends CardImpl {

    public DictateOfTheTwinGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If a source would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DictateOfTheTwinGodsEffect()));

    }

    private DictateOfTheTwinGods(final DictateOfTheTwinGods card) {
        super(card);
    }

    @Override
    public DictateOfTheTwinGods copy() {
        return new DictateOfTheTwinGods(this);
    }
}

class DictateOfTheTwinGodsEffect extends ReplacementEffectImpl {

    public DictateOfTheTwinGodsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, that source deals double that damage to that permanent or player instead";
    }

    private DictateOfTheTwinGodsEffect(final DictateOfTheTwinGodsEffect effect) {
        super(effect);
    }

    @Override
    public DictateOfTheTwinGodsEffect copy() {
        return new DictateOfTheTwinGodsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        if (damageEvent.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            Player targetPlayer = game.getPlayer(event.getTargetId());
            if (targetPlayer != null) {
                targetPlayer.damage(CardUtil.overflowInc(damageEvent.getAmount(), damageEvent.getAmount()), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                return true;
            }
        } else {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null) {
                targetPermanent.damage(CardUtil.overflowInc(damageEvent.getAmount(), damageEvent.getAmount()), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                return true;
            }
        }
        return false;
    }
}
