package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightwoodTracker extends CardImpl {

    public BrightwoodTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {5}{G}, {T}: Look at the top four cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new ManaCostsImpl<>("{5}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BrightwoodTracker(final BrightwoodTracker card) {
        super(card);
    }

    @Override
    public BrightwoodTracker copy() {
        return new BrightwoodTracker(this);
    }
}
