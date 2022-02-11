package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CandlegroveWitch extends CardImpl {

    public CandlegroveWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, Candlegrove Witch gains flying until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(), Duration.EndOfTurn
                        ), TargetController.YOU, false
                ), CovenCondition.instance, "At the beginning of combat on your turn, if you control three " +
                "or more creatures with different powers, {this} gains flying until end of turn."
        ).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private CandlegroveWitch(final CandlegroveWitch card) {
        super(card);
    }

    @Override
    public CandlegroveWitch copy() {
        return new CandlegroveWitch(this);
    }
}
