package mage.cards.c;

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

import java.util.UUID;

/**
 * @author JRHerlehy
 * Created on 4/7/18.
 */
public final class CabalStronghold extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP, "basic Swamp you control");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Basic Swamps you control", xValue);

    public CabalStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Add {B} for each basic Swamp you control.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), xValue, new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private CabalStronghold(final CabalStronghold card) {
        super(card);
    }

    @Override
    public CabalStronghold copy() {
        return new CabalStronghold(this);
    }
}
