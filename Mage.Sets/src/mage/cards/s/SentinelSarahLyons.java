package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SentinelSarahLyons extends CardImpl {

    public SentinelSarahLyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as an artifact entered the battlefield under your control this turn, creatures you control get +2/+2.
        // Battalion -- Whenever Sentinel Sarah Lyons and at least two other creatures attack, Sentinel Sarah Lyons deals damage equal the number of artifacts you control to target player.
    }

    private SentinelSarahLyons(final SentinelSarahLyons card) {
        super(card);
    }

    @Override
    public SentinelSarahLyons copy() {
        return new SentinelSarahLyons(this);
    }
}
