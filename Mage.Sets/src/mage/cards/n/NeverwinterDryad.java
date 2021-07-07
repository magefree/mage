package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeverwinterDryad extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public NeverwinterDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, Sacrifice Neverwinter Dryad: Search your library for a basic Forest card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(filter), true
                ), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NeverwinterDryad(final NeverwinterDryad card) {
        super(card);
    }

    @Override
    public NeverwinterDryad copy() {
        return new NeverwinterDryad(this);
    }
}
