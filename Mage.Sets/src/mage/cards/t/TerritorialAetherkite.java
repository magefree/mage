package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class TerritorialAetherkite extends CardImpl {

    public TerritorialAetherkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature enters, you get {E}{E}. Then you may pay one or more {E}. When you do, this creature deals that much damage to each other creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TerritorialAetherkiteEffect()));
    }

    private TerritorialAetherkite(final TerritorialAetherkite card) {
        super(card);
    }

    @Override
    public TerritorialAetherkite copy() {
        return new TerritorialAetherkite(this);
    }
}

class TerritorialAetherkiteEffect extends OneShotEffect {

    TerritorialAetherkiteEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get {E}{E}. Then you may pay one or more {E}. " +
                "When you do, this creature deals that much damage to each other creature";
    }

    TerritorialAetherkiteEffect(final TerritorialAetherkiteEffect effect) {
        super(effect);
    }

    @Override
    public TerritorialAetherkiteEffect copy() {
        return new TerritorialAetherkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent aetherkite = game.getPermanent(source.getSourceId());
        if (controller == null || aetherkite == null) {
            return false;
        }
        new GetEnergyCountersControllerEffect(2).apply(game, source);
        int energyToPay = controller.getAmount(0, controller.getCountersCount(CounterType.ENERGY),
                "Pay any amount of {E}", source, game);
        if (energyToPay == 0) {
            return true;
        }
        Cost cost = new PayEnergyCost(energyToPay);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature");
        filter.add(AnotherPredicate.instance);
        new DamageAllEffect(energyToPay, filter).apply(game, source);
        return true;
    }
}
