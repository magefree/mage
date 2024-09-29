
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;

/**
 *
 * @author TheElk801
 */
public final class LastOneStanding extends CardImpl {

    public LastOneStanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}");

        // Choose a creature at random, then destroy the rest.
        this.getSpellAbility().addEffect(new LastOneStandingEffect());
    }

    private LastOneStanding(final LastOneStanding card) {
        super(card);
    }

    @Override
    public LastOneStanding copy() {
        return new LastOneStanding(this);
    }
}

class LastOneStandingEffect extends OneShotEffect {

    LastOneStandingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a creature at random, then destroy the rest.";
    }

    private LastOneStandingEffect(final LastOneStandingEffect effect) {
        super(effect);
    }

    @Override
    public LastOneStandingEffect copy() {
        return new LastOneStandingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatureList = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        if (creatureList.size() < 2) {
            return true;
        }
        int toSave = RandomUtil.nextInt(creatureList.size());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new PermanentIdPredicate(creatureList.get(toSave).getId())));
        return new DestroyAllEffect(filter).apply(game, source);
    }
}
