package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Susucr
 */
public final class FriendlyRivalry extends CardImpl {

    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("other target legendary creature you control");

    static {
        filter2.add(new AnotherTargetPredicate(2));
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public FriendlyRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");
        

        // Target creature you control and up to one other target legendary creature you control
        // each deal damage equal to their power to target creature you don't control.
        this.getSpellAbility().addEffect(new FriendlyRivalryEffect());

        TargetControlledCreaturePermanent target1 = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(target1.setTargetTag(1));

        TargetControlledCreaturePermanent target2 = new TargetControlledCreaturePermanent(0, 1, filter2, false);
        this.getSpellAbility().addTarget(target2.setTargetTag(2));

        TargetCreaturePermanent target3 = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        this.getSpellAbility().addTarget(target3);
    }

    private FriendlyRivalry(final FriendlyRivalry card) {
        super(card);
    }

    @Override
    public FriendlyRivalry copy() {
        return new FriendlyRivalry(this);
    }
}

class FriendlyRivalryEffect extends OneShotEffect {

    FriendlyRivalryEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control and up to one other target legendary " +
            "creature you control each deal damage equal to their power to target creature you don't control.";
    }

    private FriendlyRivalryEffect(final FriendlyRivalryEffect effect) {
        super(effect);
    }

    @Override
    public FriendlyRivalryEffect copy() {
        return new FriendlyRivalryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int size = source.getTargets().size();
        if (size < 2) {
            return false;
        }

        Target damageTarget1 = source.getTargets().get(0);
        Target damageTarget2 = size == 3 ? source.getTargets().get(1) : null;

        Target destTarget = source.getTargets().get(size-1);
        if ((damageTarget1.getTargets().isEmpty() && (damageTarget2 == null || damageTarget2.getTargets().isEmpty()))
            || destTarget.getTargets().isEmpty()) {
            return false;
        }

        Permanent permanentDamage1 = damageTarget1.getTargets().isEmpty() ? null
            : game.getPermanent(damageTarget1.getTargets().get(0));
        Permanent permanentDamage2 = damageTarget2 == null || damageTarget2.getTargets().isEmpty() ? null
            : game.getPermanent(damageTarget2.getTargets().get(0));
        Permanent permanentDest = game.getPermanent(destTarget.getTargets().get(0));
        if (permanentDest == null){
            return false;
        }

        if (permanentDamage1 != null) {
            permanentDest.damage(permanentDamage1.getPower().getValue(), permanentDamage1.getId(), source, game, false, true);
        }
        if (permanentDamage2 != null) {
            permanentDest.damage(permanentDamage2.getPower().getValue(), permanentDamage2.getId(), source, game, false, true);
        }
        return true;
    }
}
