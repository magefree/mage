package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetRebelLeader extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public JetRebelLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Jet attacks, look at the top five cards of your library. You may put a creature card with mana value 3 or less from among them onto the battlefield tapped and attacking. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.BATTLEFIELD_TAPPED_ATTACKING, PutCards.BOTTOM_RANDOM
        )));
    }

    private JetRebelLeader(final JetRebelLeader card) {
        super(card);
    }

    @Override
    public JetRebelLeader copy() {
        return new JetRebelLeader(this);
    }
}
