
package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MagusOfTheCoffers extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamp you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public MagusOfTheCoffers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}, {T}: Add {B} for each Swamp you control.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), new PermanentsOnBattlefieldCount(filter), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        this.getSpellAbility().addHint(hint);
    }

    private MagusOfTheCoffers(final MagusOfTheCoffers card) {
        super(card);
    }

    @Override
    public MagusOfTheCoffers copy() {
        return new MagusOfTheCoffers(this);
    }
}
