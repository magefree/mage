
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DieYoung extends CardImpl {

    public DieYoung(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Choose target creature. You get {E}{E}, then you may pay any amount of {E}. The creature gets -1/-1 until end of turn for each {E} paid this way.
        this.getSpellAbility().addEffect(new DieYoungEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DieYoung(final DieYoung card) {
        super(card);
    }

    @Override
    public DieYoung copy() {
        return new DieYoung(this);
    }
}

class DieYoungEffect extends OneShotEffect {

    public DieYoungEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Choose target creature. You get {E}{E}, then you may pay any amount of {E}. The creature gets -1/-1 until end of turn for each {E} paid this way";
    }

    private DieYoungEffect(final DieYoungEffect effect) {
        super(effect);
    }

    @Override
    public DieYoungEffect copy() {
        return new DieYoungEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(2).apply(game, source);
            int max = controller.getCounters().getCount(CounterType.ENERGY);
            int numberToPayed = controller.getAmount(0, max, "How many energy counters do you like to pay? (maximum = " + max + ')', game);
            if (numberToPayed > 0) {
                Cost cost = new PayEnergyCost(numberToPayed);
                if (cost.pay(source, game, source, source.getControllerId(), true)) {
                    Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                    if (targetCreature != null) {
                        numberToPayed *= -1;
                        ContinuousEffect effect = new BoostTargetEffect(numberToPayed, numberToPayed, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(targetCreature, game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
