package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class StoneforgeAcolyte extends CardImpl {

    private static final FilterCard filterEquipment = new FilterCard("an Equipment card");

    static {
        filterEquipment.add(SubType.EQUIPMENT.getPredicate());
    }

    public StoneforgeAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Look at the top four cards of your library.
        // You may reveal an Equipment card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new CohortAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filterEquipment, PutCards.HAND, PutCards.BOTTOM_ANY
        )));
    }

    private StoneforgeAcolyte(final StoneforgeAcolyte card) {
        super(card);
    }

    @Override
    public StoneforgeAcolyte copy() {
        return new StoneforgeAcolyte(this);
    }
}
