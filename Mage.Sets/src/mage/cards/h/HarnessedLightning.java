
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HarnessedLightning extends CardImpl {

    public HarnessedLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose target creature. You get {E}{E}{E}, then you may pay any amount of {E}. Harnessed Lightning deals that much damage to that creature.
        this.getSpellAbility().addEffect(new HarnessedLightningEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HarnessedLightning(final HarnessedLightning card) {
        super(card);
    }

    @Override
    public HarnessedLightning copy() {
        return new HarnessedLightning(this);
    }
}

class HarnessedLightningEffect extends OneShotEffect {

    public HarnessedLightningEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Choose target creature. You get {E}{E}{E}, then you may pay any amount of {E}. {this} deals that much damage to that creature";
    }

    private HarnessedLightningEffect(final HarnessedLightningEffect effect) {
        super(effect);
    }

    @Override
    public HarnessedLightningEffect copy() {
        return new HarnessedLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(3).apply(game, source);
            int numberToPay = controller.getAmount(0, controller.getCounters().getCount(CounterType.ENERGY), "How many {E} do you like to pay?", game);
            if (numberToPay > 0) {
                Cost cost = new PayEnergyCost(numberToPay);
                if (cost.pay(source, game, source, source.getControllerId(), true)) {
                    Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                    if (targetCreature != null) {
                        targetCreature.damage(numberToPay, source.getSourceId(), source, game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
