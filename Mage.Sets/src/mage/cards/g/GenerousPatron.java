package mage.cards.g;

import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.abilities.common.PutCounterOnCreatureTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenerousPatron extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public GenerousPatron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Generous Patron enters the battlefield, support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        this.addAbility(new SupportAbility(this, 2));

        // Whenever you put one or more counters on a creature you don't control, draw a card.
        this.addAbility(new PutCounterOnCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private GenerousPatron(final GenerousPatron card) {
        super(card);
    }

    @Override
    public GenerousPatron copy() {
        return new GenerousPatron(this);
    }
}
