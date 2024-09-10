package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public final class RamosianCommander extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value 5 or less");

    static {
        filter.add(SubType.REBEL.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public RamosianCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {6}, {T}: Search your library for a Rebel permanent card with converted mana cost 5 or less and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addCost(new GenericManaCost(6));
        this.addAbility(ability);
    }

    private RamosianCommander(final RamosianCommander card) {
        super(card);
    }

    @Override
    public RamosianCommander copy() {
        return new RamosianCommander(this);
    }
}
