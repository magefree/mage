package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnsLightArcher extends CardImpl {

    public DawnsLightArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private DawnsLightArcher(final DawnsLightArcher card) {
        super(card);
    }

    @Override
    public DawnsLightArcher copy() {
        return new DawnsLightArcher(this);
    }
}
