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
public final class DragonsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Hellkite Overlord");

    static {
        filter.add(new NamePredicate("Hellkite Overlord"));
    }

    public DragonsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}, {tap}, Sacrifice a black creature, a red creature, and a green creature:
        // Search your library for a card named Hellkite Overlord and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreatureEachColor("BRG")));
        this.addAbility(ability);
    }

    private DragonsHerald(final DragonsHerald card) {
        super(card);
    }

    @Override
    public DragonsHerald copy() {
        return new DragonsHerald(this);
    }
}
