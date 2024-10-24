package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;


/**
 *
 * @author karapuzz14
 */
public final class PullOfTheMistMoon extends CardImpl {

    public PullOfTheMistMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // When Pull of the Mist Moon enters, exile target nonland permanent an opponent controls
        // until Pull of the Mist Moon leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // When Pull of the Mist Moon enters, if it was kicked, choose a nonland permanent card in your hand.
        // It perpetually gains “When this permanent enters, exile target nonland permanent an opponent controls
        // until this permanent leaves the battlefield.”
        Ability exileAbility = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        exileAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));

        FilterPermanentCard filter = new FilterPermanentCard();
        filter.add(Predicates.not(CardType.LAND.getPredicate()));

        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ChooseACardInYourHandItPerpetuallyGainsEffect(exileAbility, filter)), KickedCondition.ONCE,
                "When {this} enters, if it was kicked, choose a nonland permanent card in your hand. " +
                        "It perpetually gains \"When this permanent enters, exile target nonland permanent an opponent controls" +
                        " until this permanent leaves the battlefield.\""
        ));
    }

    private PullOfTheMistMoon(final PullOfTheMistMoon card) {
        super(card);
    }

    @Override
    public PullOfTheMistMoon copy() {
        return new PullOfTheMistMoon(this);
    }
}

