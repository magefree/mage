package mage.cards.u;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderdarkBasilisk extends CardImpl {

    public UnderdarkBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private UnderdarkBasilisk(final UnderdarkBasilisk card) {
        super(card);
    }

    @Override
    public UnderdarkBasilisk copy() {
        return new UnderdarkBasilisk(this);
    }
}
