package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SharedAnimosity extends CardImpl {

    public SharedAnimosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a creature you control attacks, it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new SharedAnimosityEffect(), false, true
        ));
    }

    private SharedAnimosity(final SharedAnimosity card) {
        super(card);
    }

    @Override
    public SharedAnimosity copy() {
        return new SharedAnimosity(this);
    }
}

class SharedAnimosityEffect extends OneShotEffect {

    SharedAnimosityEffect() {
        super(Outcome.Benefit);
        staticText = "it gets +1/+0 until end of turn for each other " +
                "attacking creature that shares a creature type with it";
    }

    private SharedAnimosityEffect(final SharedAnimosityEffect effect) {
        super(effect);
    }

    @Override
    public SharedAnimosityEffect copy() {
        return new SharedAnimosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterAttackingCreature();
        filter.add(new SharesCreatureTypePredicate(permanent));
        filter.add(AnotherPredicate.instance);
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (count > 0) {
            game.addEffect(new BoostTargetEffect(
                    count, 0, Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
