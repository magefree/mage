
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
 * @author LevelX2
 */
public class CantAttackYouUnlessPayManaAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;
    private final boolean payAlsoForAttackingPlaneswalker;

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts) {
        this(manaCosts, false);
    }

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker) {
        this(manaCosts, payAlsoForAttackingPlaneswalker, null);
    }

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, manaCosts);
        this.payAlsoForAttackingPlaneswalker = payAlsoForAttackingPlaneswalker;
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't attack you "
                + (payAlsoForAttackingPlaneswalker ? "or a planeswalker you control " : "")
                + "unless their controller pays "
                + (manaCosts == null ? "" : manaCosts.getText())
                + " for each creature they control that's attacking you";
    }

    public CantAttackYouUnlessPayManaAllEffect(final CantAttackYouUnlessPayManaAllEffect effect) {
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
    public CantAttackYouUnlessPayManaAllEffect copy() {
        return new CantAttackYouUnlessPayManaAllEffect(this);
    }
}
