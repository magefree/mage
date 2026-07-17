package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChitinousGraspling extends CardImpl {

    public ChitinousGraspling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private ChitinousGraspling(final ChitinousGraspling card) {
        super(card);
    }

    @Override
    public ChitinousGraspling copy() {
        return new ChitinousGraspling(this);
    }
}
