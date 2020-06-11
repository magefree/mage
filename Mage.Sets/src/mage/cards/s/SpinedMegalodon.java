package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinedMegalodon extends CardImpl {

    public SpinedMegalodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SHARK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever Spined Megalodon attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1), false));
    }

    private SpinedMegalodon(final SpinedMegalodon card) {
        super(card);
    }

    @Override
    public SpinedMegalodon copy() {
        return new SpinedMegalodon(this);
    }
}
