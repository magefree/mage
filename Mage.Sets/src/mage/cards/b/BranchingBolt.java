package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BranchingBolt extends CardImpl {

    private static final FilterCreaturePermanent filterNotFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterNotFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BranchingBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{G}");

        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Branching Bolt deals 3 damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING).withChooseHint("deals 3 damage, without flying"));
        // or Branching Bolt deals 3 damage to target creature without flying.
        Mode mode = new Mode(new DamageTargetEffect(3));
        mode.addTarget(new TargetPermanent(filterNotFlying).withChooseHint("deals 3 damage, without flying"));
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
