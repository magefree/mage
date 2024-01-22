package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamageAllControlledTargetEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final FilterPermanent filter;

    public DamageAllControlledTargetEffect(int amount) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public DamageAllControlledTargetEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public DamageAllControlledTargetEffect(DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;
    }

    protected DamageAllControlledTargetEffect(final DamageAllControlledTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.filter = effect.filter.copy();
    }

    @Override
    public DamageAllControlledTargetEffect copy() {
        return new DamageAllControlledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
            permanent.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, true);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} deals " + amount + " damage to each " + filter.getMessage() + ' '
                + getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + " controls";
    }
}
