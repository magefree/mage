package mage.cards.c;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.RhinoWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashTheParty extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Tapped creatures you control", xValue);

    public CrashTheParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{G}");

        // Create a tapped 4/4 green Rhino Warrior creature token for each tapped creature you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new RhinoWarriorToken(), xValue, true, false
        ));
        this.getSpellAbility().addHint(hint);
    }

    private CrashTheParty(final CrashTheParty card) {
        super(card);
    }

    @Override
    public CrashTheParty copy() {
        return new CrashTheParty(this);
    }
}
