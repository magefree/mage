package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawSecondCardTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodhazeWolverine extends CardImpl {

    public BloodhazeWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you draw your second card each turn, Bloodhaze Wolverine gets +1/+1 and gains first strike until end of turn.
        Ability ability = new DrawSecondCardTriggeredAbility(new BoostSourceEffect(
                1, 1, Duration.EndOfTurn
        ).setText("{this} gets +1/+1"), false);
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.addAbility(ability);
    }

    private BloodhazeWolverine(final BloodhazeWolverine card) {
        super(card);
    }

    @Override
    public BloodhazeWolverine copy() {
        return new BloodhazeWolverine(this);
    }
}
