package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IchorspitBasilisk extends CardImpl {

    public IchorspitBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));
    }

    private IchorspitBasilisk(final IchorspitBasilisk card) {
        super(card);
    }

    @Override
    public IchorspitBasilisk copy() {
        return new IchorspitBasilisk(this);
    }
}
