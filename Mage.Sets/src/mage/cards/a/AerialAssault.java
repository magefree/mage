package mage.cards.a;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AerialAssault extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("tapped creature");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creature you control with flying");

    static {
        filter.add(TappedPredicate.instance);
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);

    public AerialAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Destroy target tapped creature. You gain 1 life for each creature you control with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue));
    }

    private AerialAssault(final AerialAssault card) {
        super(card);
    }

    @Override
    public AerialAssault copy() {
        return new AerialAssault(this);
    }
}
