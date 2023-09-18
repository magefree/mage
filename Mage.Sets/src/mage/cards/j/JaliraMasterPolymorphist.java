package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JaliraMasterPolymorphist extends CardImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("nonlegendary creature card");

    static {
        filterCard.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public JaliraMasterPolymorphist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}, {T}, Sacrifice another creature: Reveal cards from the top of your library until you reveal a nonlegendary creature card.
        // Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new RevealCardsFromLibraryUntilEffect(
                filterCard, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true)));
        this.addAbility(ability);

    }

    private JaliraMasterPolymorphist(final JaliraMasterPolymorphist card) {
        super(card);
    }

    @Override
    public JaliraMasterPolymorphist copy() {
        return new JaliraMasterPolymorphist(this);
    }
}
