package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.FaerieDragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeywildCaretaker extends CardImpl {

    public FeywildCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Feywild Caretaker enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // At the beginning of your end step, if you have the initiative, create a 1/1 blue Faerie Dragon creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new FaerieDragonToken()), TargetController.YOU,
                HaveInitiativeCondition.instance, false
        ));
    }

    private FeywildCaretaker(final FeywildCaretaker card) {
        super(card);
    }

    @Override
    public FeywildCaretaker copy() {
        return new FeywildCaretaker(this);
    }
}
