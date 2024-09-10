package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class EyeOfUgin extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("colorless creature card");
    private static final FilterCard filterSpells = new FilterCard("Colorless Eldrazi spells");

    static {
        filter.add(ColorlessPredicate.instance);
        filterSpells.add(ColorlessPredicate.instance);
        filterSpells.add(SubType.ELDRAZI.getPredicate());
    }

    public EyeOfUgin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.supertype.add(SuperType.LEGENDARY);

        // Colorless Eldrazi spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterSpells, 2)));

        // {7}, {tap}: Search your library for a colorless creature card, reveal it, and put it into your hand. Then shuffle your library.
        Ability searchAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                new TapSourceCost());
        searchAbility.addCost(new ManaCostsImpl<>("{7}"));
        this.addAbility(searchAbility);
    }

    private EyeOfUgin(final EyeOfUgin card) {
        super(card);
    }

    @Override
    public EyeOfUgin copy() {
        return new EyeOfUgin(this);
    }
}
