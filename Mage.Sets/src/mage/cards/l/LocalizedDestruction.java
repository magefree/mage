package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author grimreap124
 */
public final class LocalizedDestruction extends CardImpl {

    public LocalizedDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{3}{W}{W}");

        // You get {E}, then you may pay one or more {E}. If you do, each creature you control with power equal to the amount of paid this way gains indestructible until end of turn.

        // You get {E}
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(1));

        // then you may pay one or more {E}. If you do, each creature you control with power equal to the amount of paid this way gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new LocalizedDestructionEffect());

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private LocalizedDestruction(final LocalizedDestruction card) {
        super(card);
    }

    @Override
    public LocalizedDestruction copy() {
        return new LocalizedDestruction(this);
    }
}

class LocalizedDestructionEffect extends OneShotEffect {

    LocalizedDestructionEffect() {
        super(Outcome.AddAbility);
        this.staticText = ", then you may pay one or more {E}. If you do, each creature you control with power equal to the amount of paid this way gains indestructible until end of turn";
    }

    private LocalizedDestructionEffect(final LocalizedDestructionEffect effect) {
        super(effect);
    }

    @Override
    public LocalizedDestructionEffect copy() {
        return new LocalizedDestructionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int totalEnergy = controller.getCountersCount(CounterType.ENERGY);
        if (totalEnergy == 0) {
            return false;
        }

        if (!controller.chooseUse(this.getOutcome(),
                "Pay 1 or more {E}? Each creature you control with power equal to the amount of paid this way gains indestructible until end of turn",
                source, game)) {
            return true;
        }

        int numberToPay = controller.getAmount(1, totalEnergy,
        "Pay one or more {E}", game);
        
        Cost cost = new PayEnergyCost(numberToPay);
        
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, numberToPay));

            game.addEffect(new GainAbilityControlledEffect(
                    IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                    filter), source);
        }
        return true;
    }
}
