package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreatureEachColor;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class AngelsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Empyrial Archangel");

    static {
        filter.add(new NamePredicate("Empyrial Archangel"));
    }

    public AngelsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{W}, {tap}, Sacrifice a green creature, a white creature, and a blue creature:
        // Search your library for a card named Empyrial Archangel and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreatureEachColor("GWU")));
        this.addAbility(ability);
    }

    private AngelsHerald(final AngelsHerald card) {
        super(card);
    }

    @Override
    public AngelsHerald copy() {
        return new AngelsHerald(this);
    }
}
