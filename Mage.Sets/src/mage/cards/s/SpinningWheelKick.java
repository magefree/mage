package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinningWheelKick extends CardImpl {

    public SpinningWheelKick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}{G}");

        // Target creature you control deals damage equal to its power to each of X target creatures and/or planeswalkers.
        this.getSpellAbility().addEffect(new SpinningWheelKickEffect());
        this.getSpellAbility().setTargetAdjuster(SpinningWheelKickAdjuster.instance);
    }

    private SpinningWheelKick(final SpinningWheelKick card) {
        super(card);
    }

    @Override
    public SpinningWheelKick copy() {
        return new SpinningWheelKick(this);
    }
}

enum SpinningWheelKickAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(
                ability.getManaCostsToPay().getX(),
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
        ));
    }
}

class SpinningWheelKickEffect extends OneShotEffect {

    SpinningWheelKickEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control deals damage equal " +
                "to its power to each of X target creatures and/or planeswalkers";
        this.setTargetPointer(new SecondTargetPointer());
    }

    private SpinningWheelKickEffect(final SpinningWheelKickEffect effect) {
        super(effect);
    }

    @Override
    public SpinningWheelKickEffect copy() {
        return new SpinningWheelKickEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(creature.getPower().getValue(), creature.getId(), source, game);
            }
        }
        return true;
    }
}
