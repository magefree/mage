
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class PhyrexianUnlife extends CardImpl {

    public PhyrexianUnlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // As long as you have 0 or less life, all damage is dealt to you as though its source had infect.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianUnlifeEffect2()));

    }

    public PhyrexianUnlife(final PhyrexianUnlife card) {
        super(card);
    }

    @Override
    public PhyrexianUnlife copy() {
        return new PhyrexianUnlife(this);
    }
}

class PhyrexianUnlifeEffect2 extends ReplacementEffectImpl {

    int lastCheckedDamageStepNum = 0;
    boolean startedWithLifeAboveZero = false;

    public PhyrexianUnlifeEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you have 0 or less life, all damage is dealt to you as though its source had infect";
    }

    public PhyrexianUnlifeEffect2(final PhyrexianUnlifeEffect2 effect) {
        super(effect);
        this.lastCheckedDamageStepNum = effect.lastCheckedDamageStepNum;
        this.startedWithLifeAboveZero = effect.startedWithLifeAboveZero;
    }

    @Override
    public PhyrexianUnlifeEffect2 copy() {
        return new PhyrexianUnlifeEffect2(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        int actualDamage = damageEvent.getAmount();
        if (actualDamage > 0) {
            Player player = game.getPlayer(damageEvent.getPlayerId());
            Permanent damageSource = game.getPermanent(damageEvent.getSourceId());
            player.addCounters(CounterType.POISON.createInstance(actualDamage), game);
            if (damageSource != null && damageSource.getAbilities().containsKey(LifelinkAbility.getInstance().getId())) {
                Player controlPlayer = game.getPlayer(damageSource.getControllerId());
                controlPlayer.gainLife(actualDamage, game, source);
            }
            game.fireEvent(new DamagedPlayerEvent(damageEvent.getPlayerId(), damageEvent.getSourceId(), damageEvent.getPlayerId(), actualDamage, damageEvent.isCombatDamage()));
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                // The decision if the player has more than 0 life has to be checked only at start of a combat damage step
                // and all damage in the same step has to be handled the same beause by the rules it's all done at once
                if (((DamagePlayerEvent) event).isCombatDamage()) {
                    if (lastCheckedDamageStepNum != game.getState().getStepNum()) {
                        lastCheckedDamageStepNum = game.getState().getStepNum();
                        startedWithLifeAboveZero = player.getLife() > 0;
                    }
                    if (startedWithLifeAboveZero) {
                        return false;
                    }
                }
                if (player.getLife() <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

}
