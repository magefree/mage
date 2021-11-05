package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiphookRaider extends CardImpl {

    public RiphookRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);
        this.nightCard = true;

        // Riphook Raider can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private RiphookRaider(final RiphookRaider card) {
        super(card);
    }

    @Override
    public RiphookRaider copy() {
        return new RiphookRaider(this);
    }
}
