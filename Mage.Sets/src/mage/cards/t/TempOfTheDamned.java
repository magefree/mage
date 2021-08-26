
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class TempOfTheDamned extends CardImpl {

    public TempOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As Temp of the Damned enters the battlefield, roll a six-sided die. Temp of the Damned enters the battlefield with a number of funk counters on it equal to the result.
        this.addAbility(new AsEntersBattlefieldAbility(new TempOfTheDamnedEffect()));
        
        // At the beginning of your upkeep, remove a funk counter from Temp of the Damned. If you can't, sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TempOfTheDamnedUpkeepEffect(), TargetController.YOU, false));
    }

    private TempOfTheDamned(final TempOfTheDamned card) {
        super(card);
    }

    @Override
    public TempOfTheDamned copy() {
        return new TempOfTheDamned(this);
    }
}

class TempOfTheDamnedEffect extends OneShotEffect {

    public TempOfTheDamnedEffect() {
        super(Outcome.Neutral);
        staticText = "roll a six-sided die. {this} enters the battlefield with a number of funk counters on it equal to the result";
    }

    public TempOfTheDamnedEffect(final TempOfTheDamnedEffect effect) {
        super(effect);
    }

    @Override
    public TempOfTheDamnedEffect copy() {
        return new TempOfTheDamnedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return new AddCountersSourceEffect(CounterType.FUNK.createInstance(controller.rollDice(Outcome.Benefit, source, game, 6))).apply(game, source);
        }
        return false;
    }
}

class TempOfTheDamnedUpkeepEffect extends OneShotEffect {

    TempOfTheDamnedUpkeepEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a funk counter from {this}. If you can't, sacrifice it";
    }

    TempOfTheDamnedUpkeepEffect(final TempOfTheDamnedUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.FUNK);
            if (amount > 0) {
                permanent.removeCounters(CounterType.FUNK.createInstance(), source, game);
            } else {
                permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TempOfTheDamnedUpkeepEffect copy() {
        return new TempOfTheDamnedUpkeepEffect(this);
    }
}
