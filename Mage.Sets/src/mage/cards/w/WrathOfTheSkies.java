package mage.cards.w;

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
import mage.players.Player;

/**
 *
 * @author DominionSpy
 */
public final class WrathOfTheSkies extends CardImpl {

    public WrathOfTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // You get X {E}, then you may pay any amount of {E}. Destroy each artifact, creature, and enchantment with mana value less than or equal to the amount of {E} paid this way.
        this.getSpellAbility().addEffect(new WrathOfTheSkiesEffect());
    }

    private WrathOfTheSkies(final WrathOfTheSkies card) {
        super(card);
    }

    @Override
    public WrathOfTheSkies copy() {
        return new WrathOfTheSkies(this);
    }
}

class WrathOfTheSkiesEffect extends OneShotEffect {

    WrathOfTheSkiesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "You get X {E}, then you may pay any amount of {E}. " +
                "Destroy each artifact, creature, and enchantment with mana value " +
                "less than or equal to the amount of {E} paid this way.";
    }

    private WrathOfTheSkiesEffect(final WrathOfTheSkiesEffect effect) {
        super(effect);
    }

    @Override
    public WrathOfTheSkiesEffect copy() {
        return new WrathOfTheSkiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        if (xValue > 0) {
            new GetEnergyCountersControllerEffect(xValue).apply(game, source);
        }

        int numberToPay = controller.getAmount(0, controller.getCounters().getCount(CounterType.ENERGY),
                "Pay any amount of {E}", game);
        Cost cost = new PayEnergyCost(numberToPay);
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            game.getBattlefield()
                    .getActivePermanents(controller.getId(), game)
                    .stream()
                    .filter(permanent -> permanent.isArtifact(game)
                            || permanent.isCreature(game)
                            || permanent.isEnchantment(game))
                    .filter(permanent -> permanent.getManaValue() <= numberToPay)
                    .forEach(permanent -> permanent.destroy(source, game, false));
        }
        return true;
    }
}
