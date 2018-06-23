package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class CabalCoffers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamp you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public CabalCoffers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {2}, {T}: Add {B} for each Swamp you control.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), new PermanentsOnBattlefieldCount(filter), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CabalCoffers(final CabalCoffers card) {
        super(card);
    }

    @Override
    public CabalCoffers copy() {
        return new CabalCoffers(this);
    }
}
