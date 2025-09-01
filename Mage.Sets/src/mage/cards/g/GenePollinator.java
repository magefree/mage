package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 * @author vcommero
 */
public final class GenePollinator extends CardImpl {

    public GenePollinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Tapped an untapped permanent you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_PERMANENT));
        this.addAbility(ability);
    }

    private GenePollinator(final GenePollinator card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new GenePollinator(this);
    }

}
