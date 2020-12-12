package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsChoicePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VolcanicOffering extends CardImpl {

    public VolcanicOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Destroy target nonbasic land you don't control and target nonbasic land of an opponent's choice you don't control.
        // Volcanic Offering deals 7 damage to target creature you don't control and 7 damage to target creature of an opponent's choice you don't control.
        this.getSpellAbility().addEffect(new VolcanicOfferingEffect());
        this.getSpellAbility().setTargetAdjuster(VolcanicOfferingAdjuster.instance);
    }

    private VolcanicOffering(final VolcanicOffering card) {
        super(card);
    }

    @Override
    public VolcanicOffering copy() {
        return new VolcanicOffering(this);
    }
}

enum VolcanicOfferingAdjuster implements TargetAdjuster {
    instance;
    private static final FilterLandPermanent filterLand = new FilterLandPermanent("nonbasic land you don't control");

    static {
        filterLand.add(TargetController.NOT_YOU.getControllerPredicate());
        filterLand.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(filterLand));
        FilterLandPermanent filterLandForOpponent = new FilterLandPermanent("nonbasic land not controlled by " + controller.getLogName());
        filterLandForOpponent.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filterLandForOpponent.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, filterLandForOpponent, false));

        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        FilterCreaturePermanent filterCreatureForOpponent = new FilterCreaturePermanent("creature not controlled by " + controller.getLogName());
        filterCreatureForOpponent.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, filterCreatureForOpponent, false));
    }
}

class VolcanicOfferingEffect extends OneShotEffect {

    VolcanicOfferingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target nonbasic land you don't control and target nonbasic land of an opponent's choice you don't control.<br>" +
                "{this} deals 7 damage to target creature you don't control and 7 damage to target creature of an opponent's choice you don't control";
    }

    private VolcanicOfferingEffect(final VolcanicOfferingEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicOfferingEffect copy() {
        return new VolcanicOfferingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.destroy(source, game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.destroy(source, game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(2).getFirstTarget());
        if (permanent != null) {
            permanent.damage(7, source.getSourceId(), source, game, false, true);
        }
        permanent = game.getPermanent(source.getTargets().get(3).getFirstTarget());
        if (permanent != null) {
            permanent.damage(7, source.getSourceId(), source, game, false, true);
        }
        return true;
    }
}
