package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.NalaarAetherjetToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class PiaNalaarChiefMechanic extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent("artifact creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PiaNalaarChiefMechanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever one or more artifact creatures you control deal combat damage to a player, you get {E}{E}.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new GetEnergyCountersControllerEffect(2), filter)
        );

        // At the beginning of your end step, you may pay one or more {E}. If you do, create an X/X colorless Vehicle artifact token named Nalaar Aetherjet with flying and crew 2, where X is the amount of {E} spent this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new PiaNalaarChiefMechanicEffect(), true));
    }

    private PiaNalaarChiefMechanic(final PiaNalaarChiefMechanic card) {
        super(card);
    }

    @Override
    public PiaNalaarChiefMechanic copy() {
        return new PiaNalaarChiefMechanic(this);
    }
}

class PiaNalaarChiefMechanicEffect extends OneShotEffect {

    public PiaNalaarChiefMechanicEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay one or more {E}. If you do, create an X/X colorless Vehicle artifact token " +
                "named Nalaar Aetherjet with flying and crew 2, where X is the amount of {E} paid this way";
    }

    public PiaNalaarChiefMechanicEffect(final PiaNalaarChiefMechanicEffect effect) {
        super(effect);
    }

    @Override
    public PiaNalaarChiefMechanicEffect copy() {
        return new PiaNalaarChiefMechanicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int energyToPay = controller.getAmount(1, controller.getCountersCount(CounterType.ENERGY),
                "Pay 1 or more {E}", source, game);
        if (energyToPay == 0) {
            return true;
        }
        Cost cost = new PayEnergyCost(energyToPay);
        if (cost.pay(source, game, source, controller.getId(), true)) {
            Token token = new NalaarAetherjetToken(energyToPay);
            token.putOntoBattlefield(1, game, source);
            return true;
        }
        return false;
    }
}
