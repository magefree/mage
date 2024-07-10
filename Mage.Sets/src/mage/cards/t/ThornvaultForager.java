package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornvaultForager extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Squirrel card");

    static {
        filter.add(SubType.SQUIRREL.getPredicate());
    }

    public ThornvaultForager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}, Forage: Add two mana in any combination of colors.
        Ability ability = new SimpleManaAbility(new AddManaInAnyCombinationEffect(5), new TapSourceCost());
        ability.addCost(new ForageCost());
        this.addAbility(ability);

        // {3}{G}, {T}: Search your library for a Squirrel card, reveal it, put it into your hand, then shuffle.
        ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                ), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThornvaultForager(final ThornvaultForager card) {
        super(card);
    }

    @Override
    public ThornvaultForager copy() {
        return new ThornvaultForager(this);
    }
}
