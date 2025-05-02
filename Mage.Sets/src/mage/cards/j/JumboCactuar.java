package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JumboCactuar extends CardImpl {

    public JumboCactuar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(7);

        // 10,000 Needles -- Whenever this creature attacks, it gets +9999/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(9999, 0, Duration.EndOfTurn, "it")
        ).withFlavorWord("10,000 Needles"));
    }

    private JumboCactuar(final JumboCactuar card) {
        super(card);
    }

    @Override
    public JumboCactuar copy() {
        return new JumboCactuar(this);
    }
}
