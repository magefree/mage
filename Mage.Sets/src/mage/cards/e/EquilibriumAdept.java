package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EquilibriumAdept extends CardImpl {

    public EquilibriumAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When this creature enters, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)));

        // Flurry -- Whenever you cast your second spell each turn, this creature gains double strike until end of turn.
        this.addAbility(new FlurryAbility(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)));
    }

    private EquilibriumAdept(final EquilibriumAdept card) {
        super(card);
    }

    @Override
    public EquilibriumAdept copy() {
        return new EquilibriumAdept(this);
    }
}
