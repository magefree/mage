package mage.cards.b;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BandTogether extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(1));
        filter2.add(new AnotherTargetPredicate(3));
    }

    public BandTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Up to two target creatures you control each deal damage equal to their power to another target creature.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        Target target = new TargetPermanent(0, 2, filter, false);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        target = new TargetPermanent(1, 1, filter2, false);
        target.setTargetTag(3);
        this.getSpellAbility().addTarget(target);
    }

    private BandTogether(final BandTogether card) {
        super(card);
    }

    @Override
    public BandTogether copy() {
        return new BandTogether(this);
    }
}
