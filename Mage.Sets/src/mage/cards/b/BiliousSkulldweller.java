package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BiliousSkulldweller extends CardImpl {

    public BiliousSkulldweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));
    }

    private BiliousSkulldweller(final BiliousSkulldweller card) {
        super(card);
    }

    @Override
    public BiliousSkulldweller copy() {
        return new BiliousSkulldweller(this);
    }
}
