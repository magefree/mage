package mage.cards.s;

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
public final class SphinxsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Sphinx Sovereign");

    static {
        filter.add(new NamePredicate("Sphinx Sovereign"));
    }

    public SphinxsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{U}, {tap}, Sacrifice a white creature, a blue creature, and a black creature:
        // Search your library for a card named Sphinx Sovereign and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreatureEachColor("WUB")));
        this.addAbility(ability);
    }

    private SphinxsHerald(final SphinxsHerald card) {
        super(card);
    }

    @Override
    public SphinxsHerald copy() {
        return new SphinxsHerald(this);
    }
}
