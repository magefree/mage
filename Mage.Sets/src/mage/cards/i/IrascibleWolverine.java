package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrascibleWolverine extends CardImpl {

    public IrascibleWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Irascible Wolverine enters the battlefield, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)));

        // Plot {2}{R}
        this.addAbility(new PlotAbility(this, "{2}{R}"));
    }

    private IrascibleWolverine(final IrascibleWolverine card) {
        super(card);
    }

    @Override
    public IrascibleWolverine copy() {
        return new IrascibleWolverine(this);
    }
}
