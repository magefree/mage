package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class ConeOfFlame extends CardImpl {

    public ConeOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Cone of Flame deals 1 damage to any target, 2 damage to another any target, and 3 damage to a third any target.
        FilterCreaturePlayerOrPlaneswalker filter1 = new FilterCreaturePlayerOrPlaneswalker("creature, player or planeswalker to deal 1 damage");
        TargetAnyTarget target1 = new TargetAnyTarget(1, 1, filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);

        FilterCreaturePlayerOrPlaneswalker filter2 = new FilterCreaturePlayerOrPlaneswalker("another creature, player or planeswalker to deal 2 damage");
        AnotherTargetPredicate predicate2 = new AnotherTargetPredicate(2);
        filter2.getCreatureFilter().add(predicate2);
        filter2.getPlayerFilter().add(predicate2);
        TargetAnyTarget target2 = new TargetAnyTarget(1, 1, filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);

        FilterCreaturePlayerOrPlaneswalker filter3 = new FilterCreaturePlayerOrPlaneswalker("another creature, player or planeswalker to deal 3 damage");
        AnotherTargetPredicate predicate3 = new AnotherTargetPredicate(3);
        filter3.getCreatureFilter().add(predicate3);
        filter3.getPlayerFilter().add(predicate3);
        TargetAnyTarget target3 = new TargetAnyTarget(1, 1, filter3);
        target3.setTargetTag(3);
        this.getSpellAbility().addTarget(target3);

        this.getSpellAbility().addEffect(new ConeOfFlameEffect());
    }

    public ConeOfFlame(final ConeOfFlame card) {
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
                applied |= (permanent.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), game) > 0);
            }
            damage++;
        }
        return applied;
    }

}
