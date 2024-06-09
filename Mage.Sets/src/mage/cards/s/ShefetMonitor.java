
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class ShefetMonitor extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or a Desert card");
    static {
        filter.add(
            Predicates.or(
                Predicates.and(
                         CardType.LAND.getPredicate(),
                         SuperType.BASIC.getPredicate()),
                SubType.DESERT.getPredicate()));
    }

    public ShefetMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Cycling {3}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}{G}")));

        // When you cycle Shefet Monitor, you may search your library for a basic land card or a Desert card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new CycleTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                true));
    }

    private ShefetMonitor(final ShefetMonitor card) {
        super(card);
    }

    @Override
    public ShefetMonitor copy() {
        return new ShefetMonitor(this);
    }
}
