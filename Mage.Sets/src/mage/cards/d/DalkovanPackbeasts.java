package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.MobilizeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DalkovanPackbeasts extends CardImpl {

    public DalkovanPackbeasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Mobilize 3
        this.addAbility(new MobilizeAbility(3));
    }

    private DalkovanPackbeasts(final DalkovanPackbeasts card) {
        super(card);
    }

    @Override
    public DalkovanPackbeasts copy() {
        return new DalkovanPackbeasts(this);
    }
}
