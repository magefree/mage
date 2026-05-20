package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IncrementAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PensiveProfessor extends CardImpl {

    public PensiveProfessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Increment
        this.addAbility(new IncrementAbility());

        // Whenever one or more +1/+1 counters are put on this creature, draw a card.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private PensiveProfessor(final PensiveProfessor card) {
        super(card);
    }

    @Override
    public PensiveProfessor copy() {
        return new PensiveProfessor(this);
    }
}
