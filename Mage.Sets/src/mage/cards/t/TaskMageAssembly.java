
package mage.cards.t;

import java.util.UUID;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class TaskMageAssembly extends CardImpl {

    public TaskMageAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When there are no creatures on the battlefield, sacrifice Task Mage Assembly.
        this.addAbility(new TaskMageAssemblyStateTriggeredAbility());

        // {2}: Task Mage Assembly deals 1 damage to target creature. Any player may activate this ability but only any time they could cast a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private TaskMageAssembly(final TaskMageAssembly card) {
        super(card);
    }

    @Override
    public TaskMageAssembly copy() {
        return new TaskMageAssembly(this);
    }
}

class TaskMageAssemblyStateTriggeredAbility extends StateTriggeredAbility {

    public TaskMageAssemblyStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public TaskMageAssemblyStateTriggeredAbility(final TaskMageAssemblyStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaskMageAssemblyStateTriggeredAbility copy() {
        return new TaskMageAssemblyStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(new FilterCreaturePermanent(), this.getControllerId(), this, game) == 0;
    }

    @Override
    public String getTriggerPhrase() {
        return "When there are no creatures on the battlefield, " ;
    }

}
