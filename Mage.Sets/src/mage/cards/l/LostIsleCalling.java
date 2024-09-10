package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostIsleCalling extends CardImpl {

    public LostIsleCalling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you scry, put a verse counter on Lost Isle Calling.
        this.addAbility(new ScryTriggeredAbility(new AddCountersSourceEffect(CounterType.VERSE.createInstance())));

        // {4}{U}{U}, Exile Lost Isle Calling: Draw a card for each verse counter on Lost Isle Calling. If it had seven or more verse counters on it, take an extra turn after this one. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new LostIsleCallingEffect(), new ManaCostsImpl<>("{4}{U}{U}")
        );
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private LostIsleCalling(final LostIsleCalling card) {
        super(card);
    }

    @Override
    public LostIsleCalling copy() {
        return new LostIsleCalling(this);
    }
}

class LostIsleCallingEffect extends OneShotEffect {

    LostIsleCallingEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each verse counter on {this}. " +
                "If it had seven or more verse counters on it, take an extra turn after this one";
    }

    private LostIsleCallingEffect(final LostIsleCallingEffect effect) {
        super(effect);
    }

    @Override
    public LostIsleCallingEffect copy() {
        return new LostIsleCallingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int count = permanent.getCounters(game).getCount(CounterType.VERSE);
        player.drawCards(count, source, game);
        if (count >= 7) {
            game.getState().getTurnMods().add(new TurnMod(player.getId()).withExtraTurn());
        }
        return true;
    }
}
