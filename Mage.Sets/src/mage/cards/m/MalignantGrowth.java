package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalignantGrowth extends CardImpl {

    public MalignantGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of your upkeep, put a growth counter on Malignant Growth.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.GROWTH.createInstance()),
                TargetController.YOU, false
        ));

        // At the beginning of each opponent's draw step, that player draws an additional card for each growth counter on Malignant Growth, then Malignant Growth deals damage to the player equal to the number of cards they drew this way.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
                new MalignantGrowthEffect(), TargetController.OPPONENT, false
        ));
    }

    private MalignantGrowth(final MalignantGrowth card) {
        super(card);
    }

    @Override
    public MalignantGrowth copy() {
        return new MalignantGrowth(this);
    }
}

class MalignantGrowthEffect extends OneShotEffect {

    MalignantGrowthEffect() {
        super(Outcome.Benefit);
        staticText = "that player draws an additional card for each growth counter on {this}, " +
                "then {this} deals damage to the player equal to the number of cards they drew this way.";
    }

    private MalignantGrowthEffect(final MalignantGrowthEffect effect) {
        super(effect);
    }

    @Override
    public MalignantGrowthEffect copy() {
        return new MalignantGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        int counters = permanent.getCounters(game).getCount(CounterType.GROWTH);
        if (counters == 0) {
            return true;
        }
        return player.damage(player.drawCards(counters, source, game), source.getSourceId(), source, game) > 0;
    }
}
