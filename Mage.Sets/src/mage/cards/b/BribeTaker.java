package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BribeTaker extends CardImpl {

    public BribeTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Bribe Taker enters the battlefield, for each kind of counter on permanents you control, you may put your choice of a +1/+1 counter or a counter of that kind on Bribe Taker.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BribeTakerEffect()));
    }

    private BribeTaker(final BribeTaker card) {
        super(card);
    }

    @Override
    public BribeTaker copy() {
        return new BribeTaker(this);
    }
}

class BribeTakerEffect extends OneShotEffect {

    private static final String plusName = CounterType.P1P1.getName();

    BribeTakerEffect() {
        super(Outcome.Benefit);
        staticText = "for each kind of counter on permanents you control, " +
                "you may put your choice of a +1/+1 counter or a counter of that kind on {this}";
    }

    private BribeTakerEffect(final BribeTakerEffect effect) {
        super(effect);
    }

    @Override
    public BribeTakerEffect copy() {
        return new BribeTakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Set<String> counterTypes = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .map(p -> p.getCounters(game))
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (counterTypes.contains(plusName)) {
            if (player.chooseUse(outcome, "Put a " + plusName +
                    " counter on " + permanent.getName() + '?', source, game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
            counterTypes.remove(plusName);
        }
        if (counterTypes.isEmpty()) {
            return true;
        }
        for (String cType : counterTypes) {
            if (!player.chooseUse(
                    outcome, "Put a " + cType + " counter or " + plusName +
                            " counter on " + permanent.getName() + '?', source, game
            )) {
                continue;
            }
            CounterType counterType = player.chooseUse(
                    outcome, "Choose " + cType + " counter or " + plusName +
                            " counter", null, cType, plusName, source, game
            ) ? CounterType.findByName(cType) : CounterType.P1P1;
            permanent.addCounters(counterType.createInstance(), source, game);
        }
        return true;
    }
}
