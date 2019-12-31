package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SageOfTheFalls extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("{this} or another non-Human creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.HUMAN)));
    }

    public SageOfTheFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Sage of the Falls or another non-Human creature enters the battlefield under you control, you may draw a card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true), filter
        ));
    }

    private SageOfTheFalls(final SageOfTheFalls card) {
        super(card);
    }

    @Override
    public SageOfTheFalls copy() {
        return new SageOfTheFalls(this);
    }
}
