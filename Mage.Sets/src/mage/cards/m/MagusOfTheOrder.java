package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagusOfTheOrder extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("green creature card");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent("another green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(AnotherPredicate.instance);
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(CardType.CREATURE.getPredicate());
    }

    public MagusOfTheOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}, {T}, Sacrifice Magus of the Order and another green creature: Search your library for a green creature card and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(1, filter), false, true
        ), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeSourceCost(), new SacrificeTargetCost(new TargetControlledPermanent(filter2)),
                "sacrifice Magus of the Order and another green creature"
        ));
        this.addAbility(ability);
    }

    private MagusOfTheOrder(final MagusOfTheOrder card) {
        super(card);
    }

    @Override
    public MagusOfTheOrder copy() {
        return new MagusOfTheOrder(this);
    }
}
