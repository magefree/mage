package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcclaimedContender extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);
    private static final FilterCard filter2
            = new FilterCard("a Knight, Aura, Equipment, or legendary artifact card");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(
                SubType.KNIGHT.getPredicate(),
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate(),
                Predicates.and(
                        SuperType.LEGENDARY.getPredicate(),
                        CardType.ARTIFACT.getPredicate()
                )
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public AcclaimedContender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Acclaimed Contender enters the battlefield, if you control another Knight, look at the top five cards of your library. You may reveal a Knight, Aura, Equipment, or legendary artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                        5, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM
                )), condition, "When {this} enters the battlefield, " +
                "if you control another Knight, look at the top five cards of your library. " +
                "You may reveal a Knight, Aura, Equipment, or legendary artifact card from among them " +
                "and put it into your hand. Put the rest on the bottom of your library in a random order."
        ));
    }

    private AcclaimedContender(final AcclaimedContender card) {
        super(card);
    }

    @Override
    public AcclaimedContender copy() {
        return new AcclaimedContender(this);
    }
}
