package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;

/**
 * "Untap (up to) X lands" effect
 */
public class UntapLandsEffect extends OneShotEffect {

    private static final FilterLandPermanent filterAll = new FilterLandPermanent("land(s) to untap");
    private static final FilterLandPermanent filterControlled = new FilterLandPermanent("land(s) to untap");

    static {
        filterAll.add(TappedPredicate.TAPPED);
        filterControlled.add(TappedPredicate.TAPPED);
        filterControlled.add(TargetController.YOU.getControllerPredicate());
    }
    private final FilterLandPermanent filter;
    private final int amount;
    private final boolean upTo;

    public UntapLandsEffect(int amount) {
        this(amount, true);
    }

    public UntapLandsEffect(int amount, boolean upTo) {
        this(amount, upTo, false);
    }

    public UntapLandsEffect(int amount, boolean upTo, boolean onlyControlled) {
        super(Outcome.Untap);
        this.amount = amount;
        this.upTo = upTo;
        this.filter = onlyControlled ? filterControlled : filterAll;
        staticText = "untap " + (upTo ? "up to " : "") + CardUtil.numberToText(amount, staticText) + " lands" + (onlyControlled ? " you control" : "");
    }

    public UntapLandsEffect(final UntapLandsEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.upTo = effect.upTo;
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedLands;
            if (upTo) {
                tappedLands = game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game).size();
            } else {
                tappedLands = game.getBattlefield().getAllActivePermanents(filter, game).size();
            }
            TargetLandPermanent target = new TargetLandPermanent(upTo ? 0 : Math.min(tappedLands, amount), amount, filter, true);
            if (target.canChoose(source.getControllerId(), source, game)) {

                // UI Shortcut: Check if any lands are already tapped.  If there are equal/fewer than amount, give the option to add those in to be untapped now.
                if (tappedLands <= amount && upTo) {
                    if (controller.chooseUse(outcome, "Include your tapped lands to untap?", source, game)) {
                        for (Permanent land : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                            target.addTarget(land.getId(), source, game);
                        }
                    }
                }
                if (target.choose(Outcome.Untap, source.getControllerId(), source.getSourceId(), source, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent p = game.getPermanent(targetId);
                        if (p != null) {
                            p.untap(game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapLandsEffect copy() {
        return new UntapLandsEffect(this);
    }
}
