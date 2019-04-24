
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanentWithDifferentTypes;

/**
 *
 * @author LevelX2
 */
public final class RivalsDuel extends CardImpl {

    public RivalsDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose two target creatures that share no creature types. Those creatures fight each other.
        this.getSpellAbility().addEffect(new RivalsDuelFightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentWithDifferentTypes(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    public RivalsDuel(final RivalsDuel card) {
        super(card);
    }

    @Override
    public RivalsDuel copy() {
        return new RivalsDuel(this);
    }
}

class RivalsDuelFightTargetsEffect extends OneShotEffect {

    public RivalsDuelFightTargetsEffect() {
        super(Outcome.Damage);
        staticText = "Choose two target creatures that share no creature types. Those creatures fight each other";
    }

    public RivalsDuelFightTargetsEffect(final RivalsDuelFightTargetsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getFirstTarget());
        Permanent creature2 = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        // 20110930 - 701.10
        if (creature1 != null
                && creature2 != null) {
            creature1.damage(creature2.getPower().getValue(), creature2.getId(), game, false, true);
            creature2.damage(creature1.getPower().getValue(), creature1.getId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public RivalsDuelFightTargetsEffect copy() {
        return new RivalsDuelFightTargetsEffect(this);
    }
}
