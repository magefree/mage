package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrotherhoodSpy extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ASSASSIN);

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary Assassin");

    public BrotherhoodSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, if you control a legendary Assassin, Brotherhood Spy gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new BoostTargetEffect(1, 0), TargetController.YOU, false
                ), condition, "At the beginning of combat on your turn, if you control a legendary Assassin, " +
                "{this} gets +1/+0 until end of turn and can't be blocked this turn."
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
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
