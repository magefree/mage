package mage.cards.g;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GracefulTakedown extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("enchanted creatures you control");
    private static final FilterControlledCreaturePermanent otherCreatureFilter = new FilterControlledCreaturePermanent("other target creature you control");
    static {
        filter.add(EnchantedPredicate.instance);
        otherCreatureFilter.add(new AnotherTargetPredicate(2));
    }

    public GracefulTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Any number of target enchanted creatures you control and up to one other target creature you control each deal damage equal to their power to target creature you don't control.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter).setTargetTag(1).withChooseHint("enchanted"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, otherCreatureFilter).setTargetTag(2));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL).setTargetTag(3));
    }

    private GracefulTakedown(final GracefulTakedown card) {
        super(card);
    }

    @Override
    public GracefulTakedown copy() {
        return new GracefulTakedown(this);
    }
}
