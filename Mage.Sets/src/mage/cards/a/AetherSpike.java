package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AetherSpike extends CardImpl {

    public AetherSpike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose target spell. You get {E}{E}, then you may pay any amount of {E}. Counter that spell unless its controller pays {1} for each {E} paid this way.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
        this.getSpellAbility().addEffect(new AetherSpikeEffect());
    }

    private AetherSpike(final AetherSpike card) {
        super(card);
    }

    @Override
    public AetherSpike copy() {
        return new AetherSpike(this);
    }
}

class AetherSpikeEffect extends OneShotEffect {

    AetherSpikeEffect() {
        super(Outcome.Detriment);
        staticText = ", then you may pay any amount of {E}. Counter that spell unless its controller pays {1} for each {E} paid this way";
    }

    private AetherSpikeEffect(final AetherSpikeEffect effect) {
        super(effect);
    }

    @Override
    public AetherSpikeEffect copy() {
        return new AetherSpikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int numberToPay = controller.getAmount(
                0, controller.getCountersCount(CounterType.ENERGY),
                "How many {E} do you want to pay?", game
        );
        Cost cost = new PayEnergyCost(numberToPay);
        int numberPaid = 0;
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            numberPaid = numberToPay;
        }
        new CounterUnlessPaysEffect(new GenericManaCost(numberPaid))
                .setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
        return true;
    }
}