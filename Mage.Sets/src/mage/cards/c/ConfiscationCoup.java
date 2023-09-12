
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ConfiscationCoup extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ARTIFACT.getPredicate()));
    }

    public ConfiscationCoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Choose target creature or artifact. You get {E}{E}{E}{E}, then you may pay an amount of {E} equal to that permanent's converted mana cost. If you do, gain control of it.
        this.getSpellAbility().addEffect(new ConfiscationCoupEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ConfiscationCoup(final ConfiscationCoup card) {
        super(card);
    }

    @Override
    public ConfiscationCoup copy() {
        return new ConfiscationCoup(this);
    }
}

class ConfiscationCoupEffect extends OneShotEffect {

    public ConfiscationCoupEffect() {
        super(Outcome.GainControl);
        this.staticText = "Choose target artifact or creature. You get {E}{E}{E}{E}, then you may pay an amount of {E} equal to that permanent's mana value. If you do, gain control of it";
    }

    private ConfiscationCoupEffect(final ConfiscationCoupEffect effect) {
        super(effect);
    }

    @Override
    public ConfiscationCoupEffect copy() {
        return new ConfiscationCoupEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(4).apply(game, source);
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                Cost cost = new PayEnergyCost(targetPermanent.getManaCost().manaValue());
                if (cost.canPay(source, source, source.getControllerId(), game)) {
                    int manaValue = targetPermanent.getManaCost().manaValue();
                    StringBuilder energy = new StringBuilder(manaValue);
                    for (int i = 0; i < manaValue; i++) {
                        energy.append("{E}");
                    }
                    if (controller.chooseUse(outcome, "Pay " + energy + " to get control of " + targetPermanent.getLogName() + '?', source, game)) {
                        if (cost.pay(source, game, source, source.getControllerId(), true)) {
                            ContinuousEffect controllEffect = new GainControlTargetEffect(Duration.Custom);
                            controllEffect.setTargetPointer(new FixedTarget(targetPermanent, game));
                            game.addEffect(controllEffect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
