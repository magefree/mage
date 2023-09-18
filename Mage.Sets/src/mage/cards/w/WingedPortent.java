package mage.cards.w;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CleaveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingedPortent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final DynamicValue xValue1 = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);
    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint1 = new ValueHint("Creatures you control", xValue1);
    private static final Hint hint2 = new ValueHint("Creatures you control with flying", xValue2);

    public WingedPortent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Cleave {4}{G}{U}
        this.addAbility(new CleaveAbility(
                this, new DrawCardSourceControllerEffect(xValue1), "{4}{G}{U}"
        ).addHint(hint1));

        // Draw a card for each creature [with flying] you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue2)
                .setText("draw a card for each creature [with flying] you control"));
        this.getSpellAbility().addHint(hint2);
    }

    private WingedPortent(final WingedPortent card) {
        super(card);
    }

    @Override
    public WingedPortent copy() {
        return new WingedPortent(this);
    }
}
