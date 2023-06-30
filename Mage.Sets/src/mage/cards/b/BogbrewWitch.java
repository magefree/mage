
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class BogbrewWitch extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Festering Newt or Bubbling Cauldron");
    static {
        filter.add(Predicates.or(new NamePredicate("Festering Newt"), new NamePredicate("Bubbling Cauldron")));
    }

    public BogbrewWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}, {T}: Search your library for a card named Festering Newt or Bubbling Cauldron, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, new FilterCard(filter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(target, true),
                new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);


    }

    private BogbrewWitch(final BogbrewWitch card) {
        super(card);
    }

    @Override
    public BogbrewWitch copy() {
        return new BogbrewWitch(this);
    }
}
