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
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageAllControlledTargetEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final FilterPermanent filter;
    private String sourceName = "{this}";

    public DamageAllControlledTargetEffect(int amount) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public DamageAllControlledTargetEffect(int amount, String whoDealDamageName) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
        this.sourceName = whoDealDamageName;
    }

    public DamageAllControlledTargetEffect(DynamicValue amount) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public DamageAllControlledTargetEffect(DynamicValue amount, String whoDealDamageName) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
        this.sourceName = whoDealDamageName;
    }

    public DamageAllControlledTargetEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public DamageAllControlledTargetEffect(DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;
    }

    public DamageAllControlledTargetEffect(final DamageAllControlledTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.filter = effect.filter.copy();
        this.sourceName = effect.sourceName;
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
        StringBuilder sb = new StringBuilder(sourceName);
        sb.append(" deals ").append(amount).append(" damage to each ").append(filter.getMessage());
        if (mode.getTargets().isEmpty()) {
            sb.append(" that player");
        } else {
            sb.append(" target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" controls");
        return sb.toString();
    }
}
