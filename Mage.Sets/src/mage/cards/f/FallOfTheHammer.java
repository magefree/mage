
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 * As Fall of the Hammer tries to resolve, if only one of the targets is legal,
 * Fall of the Hammer will still resolve but will have no effect: If the first
 * target creature is illegal, it can't deal damage to anything. If the second
 * target creature is illegal, it can't be dealt damage.
 *
 * The amount of damage dealt is based on the first target creature's power as Fall of the Hammer resolves.


 * @author LevelX2
 */
public final class FallOfTheHammer extends CardImpl {

    public FallOfTheHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Target creature you control deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new FallOfTheHammerDamageEffect());
        TargetControlledCreaturePermanent target = 
                new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("Target creature: deals damage"));
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Another creature: damage dealt to");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private FallOfTheHammer(final FallOfTheHammer card) {
        super(card);
    }

    @Override
    public FallOfTheHammer copy() {
        return new FallOfTheHammer(this);
    }
}

class FallOfTheHammerDamageEffect extends OneShotEffect {

    public FallOfTheHammerDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature you control deals damage equal to its power to another target creature";
    }

    private FallOfTheHammerDamageEffect(final FallOfTheHammerDamageEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheHammerDamageEffect copy() {
        return new FallOfTheHammerDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ownCreature = game.getPermanent(source.getFirstTarget());
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
