package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlyingDolphinFish extends CardImpl {

    public FlyingDolphinFish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.WHALE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private FlyingDolphinFish(final FlyingDolphinFish card) {
        super(card);
    }

    @Override
    public FlyingDolphinFish copy() {
        return new FlyingDolphinFish(this);
    }
}
