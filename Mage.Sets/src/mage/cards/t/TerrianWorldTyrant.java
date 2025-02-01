package mage.cards.t;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerrianWorldTyrant extends CardImpl {

    public TerrianWorldTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(9);
        this.toughness = new MageInt(7);
    }

    private TerrianWorldTyrant(final TerrianWorldTyrant card) {
        super(card);
    }

    @Override
    public TerrianWorldTyrant copy() {
        return new TerrianWorldTyrant(this);
    }
}
