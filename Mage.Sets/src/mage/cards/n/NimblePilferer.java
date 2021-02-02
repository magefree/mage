package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class NimblePilferer extends CardImpl {

    public NimblePilferer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlashAbility.getInstance());
    }

    private NimblePilferer(final NimblePilferer card) {
        super(card);
    }

    @Override
    public NimblePilferer copy() {
        return new NimblePilferer(this);
    }
}
