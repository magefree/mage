package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaintedAdversary extends CardImpl {

    public TaintedAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Tainted Adversary enters the battlefield, you may pay {2}{B} any number of times. When you pay this cost one or more times, put that many +1/+1 counters on Tainted Adversary, then create twice that many black 2/2 Zombie creature tokens with decayed.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TaintedAdversaryEffect()));
    }

    private TaintedAdversary(final TaintedAdversary card) {
        super(card);
    }

    @Override
    public TaintedAdversary copy() {
        return new TaintedAdversary(this);
    }
}

class TaintedAdversaryEffect extends OneShotEffect {

    TaintedAdversaryEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {2}{B} any number of times. When you pay this cost " +
                "one or more times, put that many +1/+1 counters on {this}, " +
                "then create twice that many black 2/2 Zombie creature tokens with decayed";
    }

    private TaintedAdversaryEffect(final TaintedAdversaryEffect effect) {
        super(effect);
    }

    @Override
    public TaintedAdversaryEffect copy() {
        return new TaintedAdversaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl<>("{2}{B}");
        int amount = 0;
        while (player.canRespond()) {
            cost.clearPaid();
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(
                    outcome, "Pay {2}{B}? You have paid this cost " +
                            amount + " time" + (amount != 1 ? "s" : ""), source, game
            ) && cost.pay(source, game, source, source.getControllerId(), false)) {
                amount++;
            }
            break;
        }
        if (amount == 0) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount)),
                false, "put that many +1/+1 counters on {this}, then create " +
                "twice that many black 2/2 Zombie creature tokens with decayed"
        );
        ability.addEffect(new CreateTokenEffect(new ZombieDecayedToken(), 2 * amount));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
