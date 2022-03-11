package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BranchingBolt extends CardImpl {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent filterNotFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterNotFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BranchingBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{G}");

        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Branching Bolt deals 3 damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterFlying).withChooseHint("deals 3 damage, without flying"));
        // or Branching Bolt deals 3 damage to target creature without flying.
        Mode mode = new Mode(new DamageTargetEffect(3));
        mode.addTarget(new TargetCreaturePermanent(filterNotFlying).withChooseHint("deals 3 damage, without flying"));
        this.getSpellAbility().addMode(mode);
    }

    private BranchingBolt(final BranchingBolt card) {
        super(card);
    }

    @Override
    public BranchingBolt copy() {
        return new BranchingBolt(this);
    }
}

class BranchingBoltEffect extends OneShotEffect {

    public BranchingBoltEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to target creature with flying and to target creature without flying";
    }

    public BranchingBoltEffect(final BranchingBoltEffect effect) {
        super(effect);
    }

    @Override
    public BranchingBoltEffect copy() {
        return new BranchingBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), source, game, false, true);
        }

        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), source, game, false, true);
        }
        return true;
    }
}
