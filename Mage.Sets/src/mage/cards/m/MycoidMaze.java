package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MycoidMaze extends CardImpl {

    public MycoidMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add(SubType.CAVE);

        // (Transforms from Twists and Turns.)
        this.nightCard = true;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {3}{G}, {T}: Look at the top four cards of your library. You may reveal a creature card from among them and put that card into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private MycoidMaze(final MycoidMaze card) {
        super(card);
    }

    @Override
    public MycoidMaze copy() {
        return new MycoidMaze(this);
    }
}
