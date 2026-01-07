package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GanglyStompling extends CardImpl {

    public GanglyStompling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private GanglyStompling(final GanglyStompling card) {
        super(card);
    }

    @Override
    public GanglyStompling copy() {
        return new GanglyStompling(this);
    }
}
