package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrotherhoodSpy extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ASSASSIN, "you control a legendary Assassin");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public BrotherhoodSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, if you control a legendary Assassin, Brotherhood Spy gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn)
        ).withInterveningIf(condition);
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn)
                .setText("and can't be blocked this turn"));
        this.addAbility(ability.addHint(hint));
    }

    private BrotherhoodSpy(final BrotherhoodSpy card) {
        super(card);
    }

    @Override
    public BrotherhoodSpy copy() {
        return new BrotherhoodSpy(this);
    }
}
