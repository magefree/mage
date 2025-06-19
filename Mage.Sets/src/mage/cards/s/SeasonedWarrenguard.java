package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedWarrenguard extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, true);
    private static final Hint hint = new ConditionHint(condition, "You control a token");

    public SeasonedWarrenguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Seasoned Warrenguard attacks while you control a token, Seasoned Warrenguard gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn)
        ).withRuleTextReplacement(false).withTriggerCondition(condition).addHint(hint));
    }

    private SeasonedWarrenguard(final SeasonedWarrenguard card) {
        super(card);
    }

    @Override
    public SeasonedWarrenguard copy() {
        return new SeasonedWarrenguard(this);
    }
}
