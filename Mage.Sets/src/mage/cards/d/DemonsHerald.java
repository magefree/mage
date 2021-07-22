package mage.cards.d;

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
 * @author North
 */
public final class DemonsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Prince of Thralls");

    static {
        filter.add(new NamePredicate("Prince of Thralls"));
    }

    public DemonsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {tap}, Sacrifice a blue creature, a black creature, and a red creature:
        // Search your library for a card named Prince of Thralls and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreatureEachColor("UBR")));
        this.addAbility(ability);
    }

    private DemonsHerald(final DemonsHerald card) {
        super(card);
    }

    @Override
    public DemonsHerald copy() {
        return new DemonsHerald(this);
    }
}
