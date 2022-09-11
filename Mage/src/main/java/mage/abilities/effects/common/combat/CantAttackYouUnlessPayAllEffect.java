
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
 * @author LevelX2
 */
public class CantAttackYouUnlessPayAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;
    private final boolean payAlsoForAttackingPlaneswalker;

    public CantAttackYouUnlessPayAllEffect(Cost cost) {
        this(cost, false);
    }

    public CantAttackYouUnlessPayAllEffect(Cost cost, boolean payAlsoForAttackingPlaneswalker) {
        this(cost, payAlsoForAttackingPlaneswalker, null);
    }

    public CantAttackYouUnlessPayAllEffect(Cost cost, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, cost);
        this.payAlsoForAttackingPlaneswalker = payAlsoForAttackingPlaneswalker;
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't attack you "
                + (payAlsoForAttackingPlaneswalker ? "or a planeswalker you control " : "")
                + "unless their controller pays "
                + (cost == null ? "" : cost.getText())
                + " for each creature they control that's attacking you";
    }

    public CantAttackYouUnlessPayAllEffect(final CantAttackYouUnlessPayAllEffect effect) {
        super(effect);
        this.payAlsoForAttackingPlaneswalker = effect.payAlsoForAttackingPlaneswalker;
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
        // attack target is controlling player
        if (source.isControlledBy(event.getTargetId())) {
            return true;
        }
        // or attack target is a planeswalker of the controlling player
        if (payAlsoForAttackingPlaneswalker) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CantAttackYouUnlessPayAllEffect copy() {
        return new CantAttackYouUnlessPayAllEffect(this);
    }
}
