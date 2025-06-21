
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LoamDryad extends CardImpl {

    public LoamDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        this.addAbility(ability);
    }

    private LoamDryad(final LoamDryad card) {
        super(card);
    }

    @Override
    public LoamDryad copy() {
        return new LoamDryad(this);
    }
}
