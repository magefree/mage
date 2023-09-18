package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EdgarMarkovsCoffinVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarMarkovsCoffin extends CardImpl {

    public EdgarMarkovsCoffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // At the beginning of your upkeep, create a 1/1 white and black Vampire creature token with lifelink and put a bloodline counter on Edgar Markov's Coffin. Then if there are three or more bloodline counters on it, remove those counters and transform it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new EdgarMarkovsCoffinVampireToken()),
                TargetController.YOU, false
        );
        ability.addEffect(new AddCountersSourceEffect(
                CounterType.BLOODLINE.createInstance()
        ).concatBy("and"));
        ability.addEffect(new EdgarMarkovsCoffinEffect());
        this.addAbility(ability);
    }

    private EdgarMarkovsCoffin(final EdgarMarkovsCoffin card) {
        super(card);
    }

    @Override
    public EdgarMarkovsCoffin copy() {
        return new EdgarMarkovsCoffin(this);
    }
}

class EdgarMarkovsCoffinEffect extends OneShotEffect {

    EdgarMarkovsCoffinEffect() {
        super(Outcome.Benefit);
        staticText = "Then if there are three or more bloodline counters on it, remove those counters and transform it";
    }

    private EdgarMarkovsCoffinEffect(final EdgarMarkovsCoffinEffect effect) {
        super(effect);
    }

    @Override
    public EdgarMarkovsCoffinEffect copy() {
        return new EdgarMarkovsCoffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int counters = permanent.getCounters(game).getCount(CounterType.BLOODLINE);
        if (counters < 3) {
            return false;
        }
        permanent.removeCounters(CounterType.BLOODLINE.createInstance(counters), source, game);
        permanent.transform(source, game);
        return true;
    }
}
