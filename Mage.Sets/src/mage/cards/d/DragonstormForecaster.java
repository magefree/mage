package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonstormForecaster extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a card named Dragonstorm Globe or Boulderborn Dragon");

    static {
        filter.add(Predicates.or(
                new NamePredicate("Dragonstorm Globe"),
                new NamePredicate("Boulderborn Dragon")
        ));
    }

    public DragonstormForecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {2}, {T}: Search your library for a card named Dragonstorm Globe or Boulderborn Dragon, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DragonstormForecaster(final DragonstormForecaster card) {
        super(card);
    }

    @Override
    public DragonstormForecaster copy() {
        return new DragonstormForecaster(this);
    }
}
