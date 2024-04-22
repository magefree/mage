
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public final class HuntingCheetah extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public HuntingCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Hunting Cheetah deals damage to an opponent, you may search your library for a Forest card, reveal that card, put it into your hand, then shuffle your library.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), true));
    }

    private HuntingCheetah(final HuntingCheetah card) {
        super(card);
    }

    @Override
    public HuntingCheetah copy() {
        return new HuntingCheetah(this);
    }
}
