
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class ZurTheEnchanter extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment card with mana value 3 or less");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ZurTheEnchanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Zur the Enchanter attacks, you may search your library for an enchantment card with converted mana cost 3 or less and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new AttacksTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false), true));
    }

    private ZurTheEnchanter(final ZurTheEnchanter card) {
        super(card);
    }

    @Override
    public ZurTheEnchanter copy() {
        return new ZurTheEnchanter(this);
    }
}
