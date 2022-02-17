
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SnakeToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RiteOfTheSerpent extends CardImpl {

    public RiteOfTheSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Destroy target creature. If that creature had a +1/+1 counter on it, create a 1/1 green Snake creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new RiteOfTheSerpentEffect());
    }

    private RiteOfTheSerpent(final RiteOfTheSerpent card) {
        super(card);
    }

    @Override
    public RiteOfTheSerpent copy() {
        return new RiteOfTheSerpent(this);
    }
}

class RiteOfTheSerpentEffect extends OneShotEffect {

    public RiteOfTheSerpentEffect() {
        super(Outcome.Benefit);
        this.staticText = "If that creature had a +1/+1 counter on it, create a 1/1 green Snake creature token";
    }

    public RiteOfTheSerpentEffect(final RiteOfTheSerpentEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfTheSerpentEffect copy() {
        return new RiteOfTheSerpentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetCreature != null) {
            if (targetCreature.getCounters(game).containsKey(CounterType.P1P1)) {
                new CreateTokenEffect(new SnakeToken()).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
