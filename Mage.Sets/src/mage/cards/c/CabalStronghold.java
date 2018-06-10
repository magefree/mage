
package mage.cards.c;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 * @author JRHerlehy
 *         Created on 4/7/18.
 */
public final class CabalStronghold extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("basic Swamp you control");

    static {
        filter.add(new SupertypePredicate(SuperType.BASIC));
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public CabalStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Add {B} for each basic Swamp you control.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), new PermanentsOnBattlefieldCount(filter), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CabalStronghold(final CabalStronghold card) {
        super(card);
    }

    @Override
    public CabalStronghold copy() {
        return new CabalStronghold(this);
    }
}
