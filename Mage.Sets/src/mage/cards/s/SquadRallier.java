package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SquadRallier extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    public SquadRallier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{W}: Look at the top four cards of your library. You may reveal a creature card with power 2 or less from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{2}{W}")));
    }

    private SquadRallier(final SquadRallier card) {
        super(card);
    }

    @Override
    public SquadRallier copy() {
        return new SquadRallier(this);
    }
}
