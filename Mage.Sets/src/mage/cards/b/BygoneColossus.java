package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BygoneColossus extends CardImpl {

    public BygoneColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Warp {3}
        this.addAbility(new WarpAbility(this, "{3}"));
    }

    private BygoneColossus(final BygoneColossus card) {
        super(card);
    }

    @Override
    public BygoneColossus copy() {
        return new BygoneColossus(this);
    }
}
