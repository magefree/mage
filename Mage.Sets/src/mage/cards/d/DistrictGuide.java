package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class DistrictGuide extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("basic land card or Gate card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.LAND.getPredicate(),
                        SuperType.BASIC.getPredicate()
                ), SubType.GATE.getPredicate()
        ));
    }

    public DistrictGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When District Guide enters the battlefield, you may search your library for a basic land card or Gate card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                ), true
        ));
    }

    private DistrictGuide(final DistrictGuide card) {
        super(card);
    }

    @Override
    public DistrictGuide copy() {
        return new DistrictGuide(this);
    }
}
