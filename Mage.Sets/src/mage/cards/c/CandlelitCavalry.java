package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

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
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        )).withInterveningIf(CovenCondition.instance).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private CandlelitCavalry(final CandlelitCavalry card) {
        super(card);
    }

    @Override
    public CandlelitCavalry copy() {
        return new CandlelitCavalry(this);
    }
}
