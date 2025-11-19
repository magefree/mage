package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author xenohedron
 */
public class DamageTargetAndAllControlledEffect extends OneShotEffect {

    private final int firstAmount;
    private final int secondAmount;
    private final FilterPermanent filter;

    /**
     * Deals simultaneous damage to the target and to each creature the target controls
     */
    public DamageTargetAndAllControlledEffect(int amount) {
        this(amount, amount, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    /**
     * Deals simultaneous damage to the target and to each creature the target controls
     */
    public DamageTargetAndAllControlledEffect(int firstAmount, int secondAmount, FilterPermanent filter) {
        super(Outcome.Damage);
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
        this.filter = filter;
    }

    protected DamageTargetAndAllControlledEffect(final DamageTargetAndAllControlledEffect effect) {
        super(effect);
        this.firstAmount = effect.firstAmount;
        this.secondAmount = effect.secondAmount;
        this.filter = effect.filter.copy();
    }

    @Override
    public DamageTargetAndAllControlledEffect copy() {
        return new DamageTargetAndAllControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(firstAmount, source.getSourceId(), source, game);
        } else {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(firstAmount, source.getSourceId(), source, game);
            }
        }
        Player controller = game.getPlayerOrPlaneswalkerController(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                perm.damage(secondAmount, source.getSourceId(), source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String description = getTargetPointer().describeTargets(mode.getTargets(), "that player");
        return "{this} deals " + firstAmount + " damage to " + description +
                " and " + secondAmount + " damage to each " + filter.getMessage() +
                " that player" +
                (description.contains("planeswalker") ? " or that planeswalker's controller" : "") +
                " controls";
    }
}
