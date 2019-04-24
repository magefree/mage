
package mage.cards.v;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class VolatileRig extends CardImpl {

    public VolatileRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Volatile Rig attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Volatile Rig is dealt damage, flip a coin. If you lose the flip, sacrifice Volatile Rig.
        this.addAbility(new VolatileRigTriggeredAbility());

        // When Volatile Rig dies, flip a coin. If you lose the flip, it deals 4 damage to each creature and each player.
        this.addAbility(new DiesTriggeredAbility(new VolatileRigEffect2()));

    }

    public VolatileRig(final VolatileRig card) {
        super(card);
    }

    @Override
    public VolatileRig copy() {
        return new VolatileRig(this);
    }
}

class VolatileRigTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggerdThisCombatStep = false;

    public VolatileRigTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VolatileRigEffect());
    }

    public VolatileRigTriggeredAbility(final VolatileRigTriggeredAbility effect) {
        super(effect);
        this.triggerdThisCombatStep = effect.triggerdThisCombatStep;
    }

    @Override
    public VolatileRigTriggeredAbility copy() {
        return new VolatileRigTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COMBAT_DAMAGE_STEP_POST
                || event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        /*
         * If Volatile Rig is dealt damage by multiple sources at the same time
         * (for example, multiple blocking creatures), its first triggered ability
         * will trigger only once.
         */
        if (triggerdThisCombatStep && event.getType() == EventType.COMBAT_DAMAGE_STEP_POST) {
            triggerdThisCombatStep = false;
        }

        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
            if (game.getPhase().getStep().getType() == PhaseStep.COMBAT_DAMAGE) {
                if (triggerdThisCombatStep) {
                    return false;
                } else {
                    triggerdThisCombatStep = true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, " + super.getRule();
    }
}

class VolatileRigEffect extends OneShotEffect {

    VolatileRigEffect() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    VolatileRigEffect(final VolatileRigEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(game)) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    return permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect copy() {
        return new VolatileRigEffect(this);
    }
}

class VolatileRigEffect2 extends OneShotEffect {

    VolatileRigEffect2() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, it deals 4 damage to each creature and each player";
    }

    VolatileRigEffect2(final VolatileRigEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(game)) {

                List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
                for (Permanent permanent : permanents) {
                    permanent.damage(4, source.getSourceId(), game, false, true);
                }
                for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
                    Player damageToPlayer = game.getPlayer(playerId);
                    if (damageToPlayer != null) {
                        damageToPlayer.damage(4, source.getSourceId(), game, false, true);
                    }
                }
                return true;

            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect2 copy() {
        return new VolatileRigEffect2(this);
    }
}
