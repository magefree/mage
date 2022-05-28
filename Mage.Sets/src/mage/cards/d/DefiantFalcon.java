
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author fireshoes
 */
public final class DefiantFalcon extends CardImpl {
    
    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value 3 or less");
    
    static {
        filter.add(SubType.REBEL.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public DefiantFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {4}, {tap}: Search your library for a Rebel permanent card with converted mana cost 3 or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DefiantFalcon(final DefiantFalcon card) {
        super(card);
    }

    @Override
    public DefiantFalcon copy() {
        return new DefiantFalcon(this);
    }
}
