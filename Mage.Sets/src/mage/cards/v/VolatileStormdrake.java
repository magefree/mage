package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofBaseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.io.ObjectStreamException;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class VolatileStormdrake extends CardImpl {

    public VolatileStormdrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof from activated and triggered abilities
        this.addAbility(VolatileStormdrakeHexproofAbility.getInstance());

        // When Volatile Stormdrake enters the battlefield, exchange control of Volatile Stormdrake and target creature an opponent controls. If you do, you get {E}{E}{E}{E}, then sacrifice that creature unless you pay an amount of {E} equal to its mana value.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VolatileStormdrakeEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private VolatileStormdrake(final VolatileStormdrake card) {
        super(card);
    }

    @Override
    public VolatileStormdrake copy() {
        return new VolatileStormdrake(this);
    }
}

class VolatileStormdrakeHexproofAbility extends HexproofBaseAbility {

    private static final VolatileStormdrakeHexproofAbility instance;

    static {
        instance = new VolatileStormdrakeHexproofAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static VolatileStormdrakeHexproofAbility getInstance() {
        return instance;
    }

    private VolatileStormdrakeHexproofAbility() {
        super();
    }

    @Override
    public boolean checkObject(MageObject sourceObject, Ability source, Game game) {
        return source.isTriggeredAbility() || source.isActivatedAbility();
    }

    @Override
    public VolatileStormdrakeHexproofAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof from activated and triggered abilities";
    }

    @Override
    public String getCardIconHint(Game game) {
        return "hexproof from activated and triggered abilities";
    }
}

class VolatileStormdrakeEffect extends OneShotEffect {

    VolatileStormdrakeEffect() {
        super(Outcome.GainControl);
        this.staticText = "exchange control of {this} and up to one target creature an opponent controls. "
                + "If you do, you get {E}{E}{E}{E}, then sacrifice that creature unless you pay an amount of {E} equal to its mana value";
    }

    private VolatileStormdrakeEffect(final VolatileStormdrakeEffect effect) {
        super(effect);
    }

    @Override
    public VolatileStormdrakeEffect copy() {
        return new VolatileStormdrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        ContinuousEffect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, "", true);
        effect.setTargetPointer(this.getTargetPointer().copy());
        game.addEffect(effect, source);
        game.getState().processAction(game);
        controller.addCounters(CounterType.ENERGY.createInstance(4), controller.getId(), source, game);
        new DoIfCostPaid(
                null, new SacrificeTargetEffect("", controller.getId()),
                new PayEnergyCost(targetPermanent.getManaValue()), true
        ).apply(game, source);
        return true;
    }
}
