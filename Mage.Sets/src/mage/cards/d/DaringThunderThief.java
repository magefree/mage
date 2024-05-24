package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaringThunderThief extends CardImpl {

    public DaringThunderThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Daring Thunder-Thief enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private DaringThunderThief(final DaringThunderThief card) {
        super(card);
    }

    @Override
    public DaringThunderThief copy() {
        return new DaringThunderThief(this);
    }
}
