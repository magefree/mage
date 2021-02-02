package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class Gigantosaurus extends CardImpl {

    public Gigantosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);
    }

    private Gigantosaurus(final Gigantosaurus card) {
        super(card);
    }

    @Override
    public Gigantosaurus copy() {
        return new Gigantosaurus(this);
    }
}
