package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangAtTheCrossroads extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public AangAtTheCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.a.AangDestinedSavior.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aang enters, look at the top five cards of your library. You may put a creature card with mana value 4 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        )));

        // When another creature you control leaves the battlefield, transform Aang at the beginning of the next upkeep.
        this.addAbility(new TransformAbility());
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new TransformSourceEffect())
        ).setText("transform {this} at the beginning of the next upkeep"), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
    }

    private AangAtTheCrossroads(final AangAtTheCrossroads card) {
        super(card);
    }

    @Override
    public AangAtTheCrossroads copy() {
        return new AangAtTheCrossroads(this);
    }
}
