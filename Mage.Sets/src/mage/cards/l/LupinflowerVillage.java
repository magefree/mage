package mage.cards.l;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LupinflowerVillage extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Bat, Bird, Mouse, or Rabbit card");

    static {
        filter.add(Predicates.or(
                SubType.BAT.getPredicate(),
                SubType.BIRD.getPredicate(),
                SubType.MOUSE.getPredicate(),
                SubType.RABBIT.getPredicate()
        ));
    }

    public LupinflowerVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {W}. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalColoredManaAbility(
                Mana.WhiteMana(1), new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_CREATURE)
        ));

        // {1}{W}, {T}, Sacrifice Lupinflower Village: Look at the top six cards of your library. You may reveal a Bat, Bird, Mouse, or Rabbit card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LupinflowerVillage(final LupinflowerVillage card) {
        super(card);
    }

    @Override
    public LupinflowerVillage copy() {
        return new LupinflowerVillage(this);
    }
}
