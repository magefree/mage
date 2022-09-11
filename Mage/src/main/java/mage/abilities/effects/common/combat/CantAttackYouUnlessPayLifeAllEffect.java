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
 * @author PurpleCrowbar
 */
public class CantAttackYouUnlessPayLifeAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;
    private final boolean payAlsoForAttackingPlaneswalker;

    public CantAttackYouUnlessPayLifeAllEffect(Cost lifeCost) {
        this(lifeCost, false);
    }

    public CantAttackYouUnlessPayLifeAllEffect(Cost lifeCost, boolean payAlsoForAttackingPlaneswalker) {
        this(lifeCost, payAlsoForAttackingPlaneswalker, null);
    }

    public CantAttackYouUnlessPayLifeAllEffect(Cost lifeCost, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, lifeCost);
        this.payAlsoForAttackingPlaneswalker = payAlsoForAttackingPlaneswalker;
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't attack you "
                + (payAlsoForAttackingPlaneswalker ? "or a planeswalker you control " : "")
                + "unless their controller pays "
                + (lifeCost == null ? "" : lifeCost.getText())
                + " for each creature they control that's attacking you";
    }

    public CantAttackYouUnlessPayLifeAllEffect(final CantAttackYouUnlessPayLifeAllEffect effect) {
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
            return permanent != null
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public CantAttackYouUnlessPayLifeAllEffect copy() {
        return new CantAttackYouUnlessPayLifeAllEffect(this);
    }
}
