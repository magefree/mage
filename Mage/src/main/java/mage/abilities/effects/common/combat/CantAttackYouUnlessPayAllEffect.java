
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2, Susucr
 */
public class CantAttackYouUnlessPayAllEffect extends PayCostToAttackBlockEffectImpl {

    public enum Scope {
        YOU_AND_CONTROLLED_PLANESWALKERS(true, true,
            "you or planeswalkers you control", "of those creatures"),
        YOU_ONLY(true, false,
            "you", "creature they control that's attacking you"),
        CONTROLLED_PLANESWALKERS_ONLY(false, true,
            "planeswalkers you control", "creature they control that's attacking a planeswalker you control");

        final boolean attackingYou;
        final boolean attackingControlledPlaneswalkers;
        // text replacing [!] in "[...] can't attack [!] unless [...] pays ..."
        final String firstText;
        // text replacing [!] in "unless their controller pays [...] for each [!]"
        final String secondText;

        Scope(boolean attackingYou, boolean attackingControlledPlaneswalkers, String firstText, String secondText) {
            this.attackingYou = attackingYou;
            this.attackingControlledPlaneswalkers = attackingControlledPlaneswalkers;
            this.firstText = firstText;
            this.secondText = secondText;
        }
    }

    private final FilterCreaturePermanent filterCreaturePermanent;
    private final Scope scope; // Describe which attacked objects this effect cares about.

    public CantAttackYouUnlessPayAllEffect(Duration duration, Cost cost) {
        this(duration, cost, Scope.YOU_ONLY);
    }

    public CantAttackYouUnlessPayAllEffect(Duration duration, Cost cost, Scope scope) {
        this(duration, cost, scope, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public CantAttackYouUnlessPayAllEffect(Duration duration, Cost cost, Scope scope, FilterCreaturePermanent filter) {
        super(duration, Outcome.Detriment, RestrictType.ATTACK, cost);
        this.scope = scope;
        this.filterCreaturePermanent = filter;

        String durationText = duration.toString();
        staticText = (durationText.isEmpty() ? "" : durationText + ", ")
                + filterCreaturePermanent.getMessage()
                + " can't attack " + scope.firstText
                + " unless their controller pays "
                + cost.getText()
                + " for each " + scope.secondText;
    }

    protected CantAttackYouUnlessPayAllEffect(final CantAttackYouUnlessPayAllEffect effect) {
        super(effect);
        this.scope = effect.scope;
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
        if (scope.attackingYou && source.isControlledBy(event.getTargetId())) {
            return true;
        }
        // or attack target is a planeswalker of the controlling player
        if (scope.attackingControlledPlaneswalkers) {
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
