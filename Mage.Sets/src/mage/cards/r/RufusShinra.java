package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.DarkstarToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RufusShinra extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you don't control a creature named Darkstar");

    static {
        filter.add(new NamePredicate("Darkstar"));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ConditionHint(condition);

    public RufusShinra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Rufus Shinra attacks, if you don't control a creature named Darkstar, create Darkstar, a legendary 2/2 white and black Dog creature token.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new DarkstarToken())
        ).withInterveningIf(condition).addHint(hint));
    }

    private RufusShinra(final RufusShinra card) {
        super(card);
    }

    @Override
    public RufusShinra copy() {
        return new RufusShinra(this);
    }
}
