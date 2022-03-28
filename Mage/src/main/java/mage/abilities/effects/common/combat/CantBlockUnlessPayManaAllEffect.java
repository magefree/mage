
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CantBlockUnlessPayManaAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts) {
        this(manaCosts, false);
    }

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker) {
        this(manaCosts, payAlsoForAttackingPlaneswalker, null);
    }

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.BLOCK, manaCosts);
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't block "
                + "unless their controller pays "
                + (manaCosts == null ? "" : manaCosts.getText())
                + " for each blocking creature they control";
    }

    public CantBlockUnlessPayManaAllEffect(CantBlockUnlessPayManaAllEffect effect) {
        super(effect);
        this.filterCreaturePermanent = effect.filterCreaturePermanent;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check if blocking creature fullfills filter criteria
        if (filterCreaturePermanent != null) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (!filterCreaturePermanent.match(permanent, source.getControllerId(), source, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CantBlockUnlessPayManaAllEffect copy() {
        return new CantBlockUnlessPayManaAllEffect(this);
    }
}
