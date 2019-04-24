package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.RiotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckingBeast extends CardImpl {

    public WreckingBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Riot
        this.addAbility(new RiotAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private WreckingBeast(final WreckingBeast card) {
        super(card);
    }

    @Override
    public WreckingBeast copy() {
        return new WreckingBeast(this);
    }
}
