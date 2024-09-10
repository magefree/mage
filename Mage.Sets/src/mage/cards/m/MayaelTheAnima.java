package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author North
 */
public final class MayaelTheAnima extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card with power 5 or greater");
    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public MayaelTheAnima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{R}{G}{W}, {tap}: Look at the top five cards of your library.
        // You may put a creature card with power 5 or greater from among them onto the battlefield.
        // Put the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_ANY),
                new ManaCostsImpl<>("{3}{R}{G}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MayaelTheAnima(final MayaelTheAnima card) {
        super(card);
    }

    @Override
    public MayaelTheAnima copy() {
        return new MayaelTheAnima(this);
    }
}
