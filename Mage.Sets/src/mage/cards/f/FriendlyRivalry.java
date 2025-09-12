package mage.cards.f;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FriendlyRivalry extends CardImpl {

    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("other target legendary creature you control");

    static {
        filter2.add(new AnotherTargetPredicate(2));
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public FriendlyRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Target creature you control and up to one other target legendary creature you control each deal damage equal to their power to target creature you don't control.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1).withChooseHint("to deal damage"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter2).setTargetTag(2).withChooseHint("to deal damage"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL).setTargetTag(3).withChooseHint("to take damage"));
    }

    private FriendlyRivalry(final FriendlyRivalry card) {
        super(card);
    }

    @Override
    public FriendlyRivalry copy() {
        return new FriendlyRivalry(this);
    }
}
