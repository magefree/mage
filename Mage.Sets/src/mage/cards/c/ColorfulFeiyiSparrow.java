package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ColorfulFeiyiSparrow extends CardImpl {

    public ColorfulFeiyiSparrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ColorfulFeiyiSparrow(final ColorfulFeiyiSparrow card) {
        super(card);
    }

    @Override
    public ColorfulFeiyiSparrow copy() {
        return new ColorfulFeiyiSparrow(this);
    }
}
