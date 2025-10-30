package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TwoOfManaColorSpentCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Deceit extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Deceit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U/B}{U/B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, if {U}{U} was spent to cast it, return up to one other target nonland permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()).withInterveningIf(TwoOfManaColorSpentCondition.BLUE);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // When this creature enters, if {B}{B} was spent to cast it, target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        ability = new EntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND))
                .withInterveningIf(TwoOfManaColorSpentCondition.BLACK);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Evoke {U/B}{U/B}
        this.addAbility(new EvokeAbility("{U/B}{U/B}"));
    }

    private Deceit(final Deceit card) {
        super(card);
    }

    @Override
    public Deceit copy() {
        return new Deceit(this);
    }
}
