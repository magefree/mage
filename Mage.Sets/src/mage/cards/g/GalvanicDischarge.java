package mage.cards.g;

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
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GalvanicDischarge extends CardImpl {

    public GalvanicDischarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Choose target creature or planeswalker. You get {E}{E}{E}, then you may pay any amount of {E}. Galvanic Discharge deals that much damage to that permanent.
        this.getSpellAbility().addEffect(new GalvanicDischargeEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private GalvanicDischarge(final GalvanicDischarge card) {
        super(card);
    }

    @Override
    public GalvanicDischarge copy() {
        return new GalvanicDischarge(this);
    }
}

class GalvanicDischargeEffect extends OneShotEffect {

    GalvanicDischargeEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose target creature or planeswalker. You get {E}{E}{E}, then you may pay any amount of {E}. {this} deals that much damage to that permanent";
    }

    private GalvanicDischargeEffect(final GalvanicDischargeEffect effect) {
        super(effect);
    }

    @Override
    public GalvanicDischargeEffect copy() {
        return new GalvanicDischargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        new GetEnergyCountersControllerEffect(3).apply(game, source);
        int numberToPay = controller.getAmount(0, controller.getCountersCount(CounterType.ENERGY), "How many {E} do you like to pay?", game);
        if (numberToPay <= 0) {
            return true;
        }
        Cost cost = new PayEnergyCost(numberToPay);
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(numberToPay, source.getSourceId(), source, game, false, true);
            }
        }
        return true;
    }
}
