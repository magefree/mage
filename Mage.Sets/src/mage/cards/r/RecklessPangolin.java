package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class RecklessPangolin extends CardImpl {

    public RecklessPangolin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PANGOLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Reckless Pangolin attacks, it gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private RecklessPangolin(final RecklessPangolin card) {
        super(card);
    }

    @Override
    public RecklessPangolin copy() {
        return new RecklessPangolin(this);
    }
}
