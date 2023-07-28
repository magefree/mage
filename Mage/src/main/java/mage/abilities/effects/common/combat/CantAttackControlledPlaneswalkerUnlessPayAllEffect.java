
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public class CantAttackControlledPlaneswalkerUnlessPayAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;

    public CantAttackControlledPlaneswalkerUnlessPayAllEffect(Cost cost) {
        this(cost, null);
    }

    public CantAttackControlledPlaneswalkerUnlessPayAllEffect(Cost cost, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, cost);
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
            + " can't attack planeswalkers you control "
            + "unless their controller pays "
            + (cost == null ? "" : cost.getText())
            + " for each creature they control that's attacking a "
            + "planeswalker you control";
    }

    public CantAttackControlledPlaneswalkerUnlessPayAllEffect(final CantAttackControlledPlaneswalkerUnlessPayAllEffect effect) {
        super(effect);
        this.filterCreaturePermanent = effect.filterCreaturePermanent;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check if attacking creature fullfills filter criteria
        if (filterCreaturePermanent != null) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (!filterCreaturePermanent.match(permanent, source.getControllerId(), source, game)) {
                return false;
            }
        }
        // attack target is a planeswalker of the controlling player
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
            && permanent.isPlaneswalker(game)
            && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public CantAttackControlledPlaneswalkerUnlessPayAllEffect copy() {
        return new CantAttackControlledPlaneswalkerUnlessPayAllEffect(this);
    }
}
