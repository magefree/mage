
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesRenownedSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class RelicSeeker extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public RelicSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Renown 1
        this.addAbility(new RenownAbility(1));

        // When Relic Seeker becomes renowned, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new BecomesRenownedSourceTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));

    }

    private RelicSeeker(final RelicSeeker card) {
        super(card);
    }

    @Override
    public RelicSeeker copy() {
        return new RelicSeeker(this);
    }
}
