package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class CryptFeaster extends CardImpl {

    public CryptFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Threshold -- Whenever this creature attacks, if there are seven or more cards in your graveyard, this creature gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn))
                .withInterveningIf(ThresholdCondition.instance).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private CryptFeaster(final CryptFeaster card) {
        super(card);
    }

    @Override
    public CryptFeaster copy() {
        return new CryptFeaster(this);
    }
}
