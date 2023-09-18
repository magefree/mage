package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonlitAmbusher extends CardImpl {

    public MoonlitAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);
        this.nightCard = true;

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private MoonlitAmbusher(final MoonlitAmbusher card) {
        super(card);
    }

    @Override
    public MoonlitAmbusher copy() {
        return new MoonlitAmbusher(this);
    }
}
