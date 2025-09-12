package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcecaveCrasher extends CardImpl {

    public IcecaveCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall -- Whenever a land you control enters, this creature gets +1/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private IcecaveCrasher(final IcecaveCrasher card) {
        super(card);
    }

    @Override
    public IcecaveCrasher copy() {
        return new IcecaveCrasher(this);
    }
}
