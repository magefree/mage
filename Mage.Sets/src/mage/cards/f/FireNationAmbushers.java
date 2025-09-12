package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationAmbushers extends CardImpl {

    public FireNationAmbushers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
    }

    private FireNationAmbushers(final FireNationAmbushers card) {
        super(card);
    }

    @Override
    public FireNationAmbushers copy() {
        return new FireNationAmbushers(this);
    }
}
