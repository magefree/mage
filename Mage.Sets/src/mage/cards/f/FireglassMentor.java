package mage.cards.f;

import mage.MageInt;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class FireglassMentor extends CardImpl {

    public FireglassMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of your second main phase, if an opponent lost life this turn, exile the top two cards of your library. Choose one of them. Until end of turn, you may play that card.
        this.addAbility(new BeginningOfSecondMainTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, true, Duration.EndOfTurn), false
        ).withInterveningIf(OpponentsLostLifeCondition.instance));
    }

    private FireglassMentor(final FireglassMentor card) {
        super(card);
    }

    @Override
    public FireglassMentor copy() {
        return new FireglassMentor(this);
    }
}
