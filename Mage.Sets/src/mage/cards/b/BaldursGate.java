package mage.cards.b;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaldursGate extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.GATE, "other Gates you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Other Gates you control", xValue);

    public BaldursGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GATE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Add X mana of any one color, where X is the number of other Gates you control.
        Ability ability = new DynamicManaAbility(
                Mana.AnyMana(1), xValue, new GenericManaCost(2), null, true
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private BaldursGate(final BaldursGate card) {
        super(card);
    }

    @Override
    public BaldursGate copy() {
        return new BaldursGate(this);
    }
}
