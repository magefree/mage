package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CandlelitCavalry extends CardImpl {

    public CandlelitCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, Candlelit Cavalry gains trample until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new GainAbilitySourceEffect(
                                TrampleAbility.getInstance(), Duration.EndOfTurn
                        ), TargetController.YOU, false
                ), CovenCondition.instance, AbilityWord.COVEN.formatWord() +
                "At the beginning of combat on your turn, if you control three or more creatures " +
                "with different powers, {this} gains trample until end of turn."
        ).addHint(CovenHint.instance));
    }

    private CandlelitCavalry(final CandlelitCavalry card) {
        super(card);
    }

    @Override
    public CandlelitCavalry copy() {
        return new CandlelitCavalry(this);
    }
}
