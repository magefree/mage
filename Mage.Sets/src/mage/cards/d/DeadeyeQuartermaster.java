
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class DeadeyeQuartermaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment or Vehicle card");

    static {
        filter.add(Predicates.or(
                SubType.EQUIPMENT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public DeadeyeQuartermaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Deadeye Quartermaster enters the battlefield, you may search your library for an Equipment or a Vehicle card and put it into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private DeadeyeQuartermaster(final DeadeyeQuartermaster card) {
        super(card);
    }

    @Override
    public DeadeyeQuartermaster copy() {
        return new DeadeyeQuartermaster(this);
    }
}
