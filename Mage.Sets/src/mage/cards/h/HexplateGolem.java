package mage.cards.h;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HexplateGolem extends CardImpl {

    public HexplateGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);
    }

    private HexplateGolem(final HexplateGolem card) {
        super(card);
    }

    @Override
    public HexplateGolem copy() {
        return new HexplateGolem(this);
    }
}
