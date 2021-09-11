
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fenhl
 */
public final class ParallelEvolution extends CardImpl {

    public ParallelEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // For each creature token on the battlefield, its controller creates a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new ParallelEvolutionEffect());

        // Flashback {4}{G}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{4}{G}{G}{G}")));
    }

    private ParallelEvolution(final ParallelEvolution card) {
        super(card);
    }

    @Override
    public ParallelEvolution copy() {
        return new ParallelEvolution(this);
    }
}

class ParallelEvolutionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public ParallelEvolutionEffect() {
        super(Outcome.Neutral);
        this.staticText = "For each creature token on the battlefield, its controller creates a token that's a copy of that creature";
    }

    public ParallelEvolutionEffect(final ParallelEvolutionEffect effect) {
        super(effect);
    }

    @Override
    public ParallelEvolutionEffect copy() {
        return new ParallelEvolutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(permanent.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
        }
        return true;
    }
}
