
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class TamiyosJournal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Clues");

    static {
        filter.add(SubType.CLUE.getPredicate());
    }

    public TamiyosJournal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, investigate (Create a colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\").
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new InvestigateEffect(), TargetController.YOU, false));

        // {T}, Sacrifice three Clues: Search your library for a card and put that card into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, false)));
        this.addAbility(ability);
    }

    private TamiyosJournal(final TamiyosJournal card) {
        super(card);
    }

    @Override
    public TamiyosJournal copy() {
        return new TamiyosJournal(this);
    }
}
