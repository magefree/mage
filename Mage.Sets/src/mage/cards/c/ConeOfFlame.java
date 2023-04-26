package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class ConeOfFlame extends CardImpl {

    private static final FilterPermanentOrPlayer filter1 = new FilterAnyTarget("any target to deal 1 damage");
    private static final FilterPermanentOrPlayer filter2 = new FilterAnyTarget("another target to deal 2 damage");
    private static final FilterPermanentOrPlayer filter3 = new FilterAnyTarget("third target to deal 3 damage");

    static {
        filter2.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
        filter3.getPermanentFilter().add(new AnotherTargetPredicate(3));
        filter3.getPlayerFilter().add(new AnotherTargetPredicate(3));
    }

    public ConeOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Cone of Flame deals 1 damage to any target, 2 damage to another target, and 3 damage to a third target.
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter1).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter2).setTargetTag(2));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter3).setTargetTag(3));
        this.getSpellAbility().addEffect(new ConeOfFlameEffect());
    }

    private ConeOfFlame(final ConeOfFlame card) {
        super(card);
    }

    @Override
    public ConeOfFlame copy() {
        return new ConeOfFlame(this);
    }
}

class ConeOfFlameEffect extends OneShotEffect {

    public ConeOfFlameEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to any target, "
                + "2 damage to another target, "
                + "and 3 damage to a third target";
    }

    public ConeOfFlameEffect(final ConeOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public ConeOfFlameEffect copy() {
        return new ConeOfFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        int damage = 1;
        for (Target target : source.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                applied |= (permanent.damage(damage, source.getSourceId(), source, game, false, true) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), source, game) > 0);
            }
            damage++;
        }
        return applied;
    }

}
