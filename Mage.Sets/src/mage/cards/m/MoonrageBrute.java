package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonrageBrute extends CardImpl {

    public MoonrageBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Ward—Pay 3 life.
        this.addAbility(new WardAbility(new PayLifeCost(3), false));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private MoonrageBrute(final MoonrageBrute card) {
        super(card);
    }

    @Override
    public MoonrageBrute copy() {
        return new MoonrageBrute(this);
    }
}
