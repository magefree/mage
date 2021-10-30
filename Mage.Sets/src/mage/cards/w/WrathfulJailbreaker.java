package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrathfulJailbreaker extends CardImpl {

    public WrathfulJailbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setRed(true);
        this.nightCard = true;

        // Wrathful Jailbreaker attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private WrathfulJailbreaker(final WrathfulJailbreaker card) {
        super(card);
    }

    @Override
    public WrathfulJailbreaker copy() {
        return new WrathfulJailbreaker(this);
    }
}
