
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class SilvergladeElemental extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public SilvergladeElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true), true));
    }

    private SilvergladeElemental(final SilvergladeElemental card) {
        super(card);
    }

    @Override
    public SilvergladeElemental copy() {
        return new SilvergladeElemental(this);
    }
}
