
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class DeadlyDesigns extends CardImpl {

    public DeadlyDesigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // {2}: Put a plot counter on Deadly Designs. Any player may activate this ability.
        SimpleActivatedAbility activatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PLOT.createInstance()), new GenericManaCost(2));
        activatedAbility.setMayActivate(TargetController.ANY);
        activatedAbility.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(activatedAbility);

        // When there are five or more plot counters on Deadly Designs, sacrifice it. If you do, destroy up to two target creatures.
        StateTriggeredAbility triggerredAbility = new DeadlyDesignsTriggerAbility();
        triggerredAbility.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(triggerredAbility);
    }

    private DeadlyDesigns(final DeadlyDesigns card) {
        super(card);
    }

    @Override
    public DeadlyDesigns copy() {
        return new DeadlyDesigns(this);
    }
}

class DeadlyDesignsTriggerAbility extends StateTriggeredAbility {

    public DeadlyDesignsTriggerAbility() {
        super(Zone.BATTLEFIELD, new DeadlyDesignsEffect());
    }

    private DeadlyDesignsTriggerAbility(final DeadlyDesignsTriggerAbility ability) {
        super(ability);
    }

    @Override
    public DeadlyDesignsTriggerAbility copy() {
        return new DeadlyDesignsTriggerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.PLOT) > 4;
    }

    @Override
    public String getRule() {
        return "When there are five or more plot counters on {this}, sacrifice it. If you do, destroy up to two target creatures";
    }
}

class DeadlyDesignsEffect extends SacrificeSourceEffect {

    private boolean sacrificed = false;

    public DeadlyDesignsEffect() {
        super();
    }

    private DeadlyDesignsEffect(final DeadlyDesignsEffect effect) {
        super(effect);
        this.sacrificed = effect.sacrificed;
    }

    @Override
    public DeadlyDesignsEffect copy() {
        return new DeadlyDesignsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        sacrificed = super.apply(game, source);
        if (sacrificed) {
            Permanent toDestroy;
            for (UUID target : getTargetPointer().getTargets(game, source)) {
                toDestroy = game.getPermanent(target);
                if (toDestroy != null) {
                    toDestroy.destroy(source, game, false);
                }
            }
        }
        return sacrificed;
    }

    public boolean isSacrificed() {
        return sacrificed;
    }
}
