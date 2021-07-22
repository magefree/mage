
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Backfir3
 */
public final class BogGlider extends CardImpl {

    static final FilterControlledPermanent landFilter = new FilterControlledLandPermanent("a land");
    private static final FilterPermanentCard filter = new FilterPermanentCard("Mercenary permanent card with mana value 2 or less");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public BogGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN, SubType.MERCENARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}, Sacrifice a land: Search your library for a Mercenary permanent card with converted mana cost 2 or less and put it onto the battlefield. Then shuffle your library.
	Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(landFilter)));
	this.addAbility(ability);
    }

    private BogGlider(final BogGlider card) {
        super(card);
    }

    @Override
    public BogGlider copy() {
        return new BogGlider(this);
    }
}