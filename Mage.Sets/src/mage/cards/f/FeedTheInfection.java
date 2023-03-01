package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public final class FeedTheInfection extends CardImpl {

    public FeedTheInfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // You draw three cards and lose 3 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3, "you"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3).concatBy("and"));

        // Corrupted -- Each opponent with three or more poison counters loses 3 life.
        this.getSpellAbility().addEffect(new FeedTheInfectionEffect());
    }

    private FeedTheInfection(final FeedTheInfection card) {
        super(card);
    }

    @Override
    public FeedTheInfection copy() {
        return new FeedTheInfection(this);
    }
}

class FeedTheInfectionEffect extends OneShotEffect {

    FeedTheInfectionEffect() {
        super(Outcome.Benefit);
        staticText = "<br>" + AbilityWord.CORRUPTED.formatWord() +
                "Each opponent with three or more poison counters loses 3 life";
    }

    private FeedTheInfectionEffect(final FeedTheInfectionEffect effect) {
        super(effect);
    }

    @Override
    public FeedTheInfectionEffect copy() {
        return new FeedTheInfectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getCounters().getCount(CounterType.POISON) >= 3) {
                player.loseLife(3, game, source, false);
            }
        }
        return true;
    }
}
