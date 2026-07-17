package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IncrementAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuboidColony extends CardImpl {

    public CuboidColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Increment
        this.addAbility(new IncrementAbility());
    }

    private CuboidColony(final CuboidColony card) {
        super(card);
    }

    @Override
    public CuboidColony copy() {
        return new CuboidColony(this);
    }
}
