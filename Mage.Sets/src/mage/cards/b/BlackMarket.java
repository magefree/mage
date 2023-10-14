
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author markedagain
 */
public final class BlackMarket extends CardImpl {

    public BlackMarket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a creature dies, put a charge counter on Black Market.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), false));
        // At the beginning of your precombat main phase, add {B} for each charge counter on Black Market.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new BlackMarketEffect(), TargetController.YOU, false));

    }

    private BlackMarket(final BlackMarket card) {
        super(card);
    }

    @Override
    public BlackMarket copy() {
        return new BlackMarket(this);
    }
}

class BlackMarketEffect extends OneShotEffect {

    public BlackMarketEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add {B} for each charge counter on Black Market";
    }

    private BlackMarketEffect(final BlackMarketEffect effect) {
        super(effect);
    }

    @Override
    public BlackMarketEffect copy() {
        return new BlackMarketEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && player != null) {
            int chargeCounters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
            if (chargeCounters > 0) {
                player.getManaPool().addMana(Mana.BlackMana(chargeCounters), game, source);
            }
            return true;
        }
        return false;
    }
}
