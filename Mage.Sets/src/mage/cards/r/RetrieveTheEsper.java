package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.RobotBlueToken;
import mage.game.permanent.token.Token;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RetrieveTheEsper extends CardImpl {

    public RetrieveTheEsper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Create a 3/3 blue Robot Warrior artifact creature token. Then if this spell was cast from a graveyard, put two +1/+1 counters on that token.
        this.getSpellAbility().addEffect(new RetrieveTheEsperEffect());

        // Flashback {5}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}")));
    }

    private RetrieveTheEsper(final RetrieveTheEsper card) {
        super(card);
    }

    @Override
    public RetrieveTheEsper copy() {
        return new RetrieveTheEsper(this);
    }
}

class RetrieveTheEsperEffect extends OneShotEffect {

    RetrieveTheEsperEffect() {
        super(Outcome.Benefit);
        staticText = "create a 3/3 blue Robot Warrior artifact creature token. " +
                "Then if this spell was cast from a graveyard, put two +1/+1 counters on that token";
    }

    private RetrieveTheEsperEffect(final RetrieveTheEsperEffect effect) {
        super(effect);
    }

    @Override
    public RetrieveTheEsperEffect copy() {
        return new RetrieveTheEsperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RobotBlueToken();
        token.putOntoBattlefield(1, game, source);
        if (!CastFromGraveyardSourceCondition.instance.apply(game, source)) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Optional.ofNullable(tokenId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(
                            CounterType.P1P1.createInstance(2), source, game
                    ));
        }
        return true;
    }
}
