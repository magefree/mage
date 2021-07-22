package mage.cards.b;

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
public final class BehemothsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Godsire");

    static {
        filter.add(new NamePredicate("Godsire"));
    }

    public BehemothsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}, Sacrifice a red creature, a green creature, and a white creature:
        // Search your library for a card named Godsire and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreatureEachColor("RGW")));
        this.addAbility(ability);
    }

    private BehemothsHerald(final BehemothsHerald card) {
        super(card);
    }

    @Override
    public BehemothsHerald copy() {
        return new BehemothsHerald(this);
    }
}
