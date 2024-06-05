package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EtchedSlith extends CardImpl {

    public EtchedSlith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SLITH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Etched Slith deals combat damage to a player, put a +1/+1 counter on it. When you do, you may remove a counter from another target permanent or opponent.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new EtchedSlithPutWhenDoEffect()
        ));
    }

    private EtchedSlith(final EtchedSlith card) {
        super(card);
    }

    @Override
    public EtchedSlith copy() {
        return new EtchedSlith(this);
    }
}

class EtchedSlithPutWhenDoEffect extends OneShotEffect {


    private static final FilterPermanent filterPermanent = new FilterPermanent();

    static {
        filterPermanent.add(AnotherPredicate.instance);
    }

    private static final FilterPermanentOrPlayer filter =
            new FilterPermanentOrPlayer(
                    "another target permanent or player",
                    filterPermanent, new FilterPlayer()
            );

    EtchedSlithPutWhenDoEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on it. When you do, you may remove a counter from another target permanent or opponent";
    }

    private EtchedSlithPutWhenDoEffect(final EtchedSlithPutWhenDoEffect effect) {
        super(effect);
    }

    @Override
    public EtchedSlithPutWhenDoEffect copy() {
        return new EtchedSlithPutWhenDoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        int beforeP1P1 = permanent.getCounters(game).getCount(CounterType.P1P1);
        new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
        game.getState().processAction(game);
        int afterP1P1 = permanent.getCounters(game).getCount(CounterType.P1P1);
        for (int i = beforeP1P1 + 1; i <= afterP1P1; ++i) {
            // Releases notes rulling:
            // > In a case where you put more than one +1/+1 counter on Etched Slith with its last ability
            // (for example, because of the effect of Doubling Season), the reflexive ability will trigger
            // once for each +1/+1 counter you put on it this way.
            ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new EtchedSlithEffect(), false);
            reflexive.addTarget(new TargetPermanentOrPlayer(filter));
            game.fireReflexiveTriggeredAbility(reflexive, source);
        }
        return true;
    }
}

// Price of Betrayal as inspiration.
class EtchedSlithEffect extends OneShotEffect {

    EtchedSlithEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "you may remove a counter from another target permanent or opponent.";
    }

    private EtchedSlithEffect(final EtchedSlithEffect effect) {
        super(effect);
    }

    @Override
    public EtchedSlithEffect copy() {
        return new EtchedSlithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // from permanent
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (controller.chooseUse(Outcome.Neutral, "Remove a " + counterName + " counter?", source, game)) {
                    permanent.removeCounters(counterName, 1, source, game);
                    break;
                }
            }
            return true;
        }

        // from player
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (Counter counter : player.getCountersAsCopy().values()) {
                String counterName = counter.getName();
                if (controller.chooseUse(Outcome.Neutral, "Remove a " + counterName + " counter?", source, game)) {
                    player.loseCounters(counterName, 1, source, game);
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
