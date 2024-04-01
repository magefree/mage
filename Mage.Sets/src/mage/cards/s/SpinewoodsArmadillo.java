package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinewoodsArmadillo extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or a Desert card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ), SubType.DESERT.getPredicate()
        ));
    }

    public SpinewoodsArmadillo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ARMADILLO);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // {1}{G}, Discard Spinewoods Armadillo: Search your library for a basic land card or a Desert card, reveal it, put it into your hand, then shuffle. You gain 3 life.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addEffect(new GainLifeEffect(3));
        this.addAbility(ability);
    }

    private SpinewoodsArmadillo(final SpinewoodsArmadillo card) {
        super(card);
    }

    @Override
    public SpinewoodsArmadillo copy() {
        return new SpinewoodsArmadillo(this);
    }
}
