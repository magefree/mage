package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class TezzeretsGatebreaker extends CardImpl {

    private static final FilterCard filter = new FilterCard("a blue or artifact card");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public TezzeretsGatebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Tezzeret's Gatebreaker enters the battlefield, look at the top five cards of your library. You may reveal a blue or artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)));

        // {5}{U}, {T}, Sacrifice Tezzeret's Gatebreaker: Creatures you control can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedAllEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        Duration.EndOfTurn
                ),
                new ManaCostsImpl<>("{5}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TezzeretsGatebreaker(final TezzeretsGatebreaker card) {
        super(card);
    }

    @Override
    public TezzeretsGatebreaker copy() {
        return new TezzeretsGatebreaker(this);
    }
}
