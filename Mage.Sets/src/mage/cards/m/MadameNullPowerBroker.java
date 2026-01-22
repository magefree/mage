package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MadameNullPowerBroker extends CardImpl {

    public MadameNullPowerBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature you control enters, you may pay life equal to its power. If you do, put that many +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new MadameNullPowerBrokerEffect(), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true
        ));
    }

    private MadameNullPowerBroker(final MadameNullPowerBroker card) {
        super(card);
    }

    @Override
    public MadameNullPowerBroker copy() {
        return new MadameNullPowerBroker(this);
    }
}

class MadameNullPowerBrokerEffect extends OneShotEffect {

    MadameNullPowerBrokerEffect() {
        super(Outcome.Benefit);
        staticText = "pay life equal to its power. If you do, put that many +1/+1 counters on it";
    }

    private MadameNullPowerBrokerEffect(final MadameNullPowerBrokerEffect effect) {
        super(effect);
    }

    @Override
    public MadameNullPowerBrokerEffect copy() {
        return new MadameNullPowerBrokerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (player == null || permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1 || !CardUtil.tryPayLife(power, player, source, game)) {
            return false;
        }
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .ifPresent(p -> p.addCounters(CounterType.P1P1.createInstance(power), source, game));
        return true;
    }
}
