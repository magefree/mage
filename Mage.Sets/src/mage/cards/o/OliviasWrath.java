package mage.cards.o;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OliviasWrath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.VAMPIRE);

    static {
        filter.add(Predicates.or(SubType.VAMPIRE.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, -1);
    private static final Hint hint = new ValueHint(
            "Vampires you control", new PermanentsOnBattlefieldCount(filter2)
    );

    public OliviasWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Each non-Vampire creature gets -X/-X until end of turn, where X is the number of Vampires you control.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn, filter, false,
                "each non-Vampire creature gets -X/-X until end of turn, " +
                        "where X is the number of Vampires you control", true
        ));
    }

    private OliviasWrath(final OliviasWrath card) {
        super(card);
    }

    @Override
    public OliviasWrath copy() {
        return new OliviasWrath(this);
    }
}
