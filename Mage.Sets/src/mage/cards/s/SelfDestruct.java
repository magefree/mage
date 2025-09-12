package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAnyTarget;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelfDestruct extends CardImpl {

    private static final FilterAnyTarget filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public SelfDestruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature you control deals X damage to any other target and X damage to itself, where X is its power.
        this.getSpellAbility().addEffect(new SelfDestructEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter).setTargetTag(2));
    }

    private SelfDestruct(final SelfDestruct card) {
        super(card);
    }

    @Override
    public SelfDestruct copy() {
        return new SelfDestruct(this);
    }
}

class SelfDestructEffect extends OneShotEffect {

    SelfDestructEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control deals X damage " +
                "to any other target and X damage to itself, where X is its power";
        this.setTargetPointer(new EachTargetPointer());
    }

    private SelfDestructEffect(final SelfDestructEffect effect) {
        super(effect);
    }

    @Override
    public SelfDestructEffect copy() {
        return new SelfDestructEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targets = this.getTargetPointer().getTargets(game, source);
        if (targets.size() < 2) {
            return false;
        }
        Permanent creature = game.getPermanent(targets.get(0));
        if (creature == null) {
            return false;
        }
        int power = creature.getPower().getValue();
        if (power < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(targets.get(1));
        if (permanent != null) {
            permanent.damage(power, creature.getId(), source, game);
        }
        Player player = game.getPlayer(targets.get(1));
        if (player != null) {
            player.damage(power, creature.getId(), source, game);
        }
        permanent.damage(power, permanent.getId(), source, game);
        return true;
    }
}
