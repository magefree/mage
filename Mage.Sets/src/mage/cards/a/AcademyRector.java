
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class AcademyRector extends CardImpl {

    public AcademyRector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DoIfCostPaid(
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterEnchantmentCard()), false, true),
                        new ExileSourceFromGraveCost(),
                        "Exile to search for an enchantment?"
                ), false
        ));
    }

    private AcademyRector(final AcademyRector card) {
        super(card);
    }

    @Override
    public AcademyRector copy() {
        return new AcademyRector(this);
    }
}
