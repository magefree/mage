
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class StoneforgeAcolyte extends CardImpl {

    private static final FilterControlledPermanent filterAlly = new FilterControlledPermanent("an untapped Ally you control");
    private static final FilterCard filterEquipment = new FilterCard("an Equipment card");

    static {
        filterAlly.add(SubType.ALLY.getPredicate());
        filterAlly.add(TappedPredicate.UNTAPPED);
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
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(4, 1, filterEquipment, PutCards.HAND, PutCards.BOTTOM_ANY),
                new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filterAlly)));
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private StoneforgeAcolyte(final StoneforgeAcolyte card) {
        super(card);
    }

    @Override
    public StoneforgeAcolyte copy() {
        return new StoneforgeAcolyte(this);
    }
}
