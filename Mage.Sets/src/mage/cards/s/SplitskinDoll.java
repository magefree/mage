package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplitskinDoll extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ConditionHint(
            new InvertCondition(condition), "You control another creature with power 2 or less"
    );

    public SplitskinDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Splitskin Doll enters, draw a card. Then discard a card unless you control another creature with power 2 or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), condition, "then discard " +
                "a card unless you control another creature with power 2 or less"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private SplitskinDoll(final SplitskinDoll card) {
        super(card);
    }

    @Override
    public SplitskinDoll copy() {
        return new SplitskinDoll(this);
    }
}
