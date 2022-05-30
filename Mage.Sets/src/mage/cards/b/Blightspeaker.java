
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class Blightspeaker extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value 3 or less");
    
    static {
        filter.add(SubType.REBEL.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public Blightspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.REBEL, SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
        // {4}, {tap}: Search your library for a Rebel permanent card with converted mana cost 3 or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                new ManaCostsImpl<>("{4}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    private Blightspeaker(final Blightspeaker card) {
        super(card);
    }

    @Override
    public Blightspeaker copy() {
        return new Blightspeaker(this);
    }
}
