package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaramirStewardOfGondor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a legendary creature you control with mana value 4 or greater");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public FaramirStewardOfGondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a legendary creature with mana value 4 or greater you control enters, you become the monarch.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new BecomesMonarchSourceEffect(), filter).addHint(MonarchHint.instance));

        // At the beginning of your end step, if you're the monarch, create two 1/1 white Human Soldier creature tokens.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new HumanSoldierToken(), 2),
                false, MonarchIsSourceControllerCondition.instance
        ));
    }

    private FaramirStewardOfGondor(final FaramirStewardOfGondor card) {
        super(card);
    }

    @Override
    public FaramirStewardOfGondor copy() {
        return new FaramirStewardOfGondor(this);
    }
}
