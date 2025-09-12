package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class GrowingRitesOfItlimoc extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control four or more creatures"),
            ComparisonType.MORE_THAN, 3
    );

    public GrowingRitesOfItlimoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.i.ItlimocCradleOfTheSun.class;

        // When Growing Rites of Itlimoc enters the battlefield, look at the top four cards of your library.
        // You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY
        )));

        // At the beginning of your end step, if you control four or more creatures, transform Growing Rites of Itlimoc.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(CreaturesYouControlHint.instance));
    }

    private GrowingRitesOfItlimoc(final GrowingRitesOfItlimoc card) {
        super(card);
    }

    @Override
    public GrowingRitesOfItlimoc copy() {
        return new GrowingRitesOfItlimoc(this);
    }
}
