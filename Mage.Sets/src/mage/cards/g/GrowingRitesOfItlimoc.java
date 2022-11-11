package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 * @author JRHerlehy
 */
public final class GrowingRitesOfItlimoc extends CardImpl {

    public GrowingRitesOfItlimoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.i.ItlimocCradleOfTheSun.class;

        // When Growing Rites of Itlimoc enters the battlefield, look at the top four cards of your library.
        // You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY)));

        // At the beginning of your end step, if you control four or more creatures, transform Growing Rites of Itlimoc.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_A_CREATURE, ComparisonType.MORE_THAN, 3),
                "At the beginning of your end step, if you control four or more creatures, transform {this}"));
    }

    private GrowingRitesOfItlimoc(final GrowingRitesOfItlimoc card) {
        super(card);
    }

    @Override
    public GrowingRitesOfItlimoc copy() {
        return new GrowingRitesOfItlimoc(this);
    }
}
