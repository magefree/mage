
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksCreatureWithFlyingTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class EzurisArchers extends CardImpl {

    public EzurisArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Ezuri's Archers blocks a creature with flying, Ezuri's Archers gets +3/+0 until end of turn.
        this.addAbility(new BlocksCreatureWithFlyingTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), false));
    }

    private EzurisArchers(final EzurisArchers card) {
        super(card);
    }

    @Override
    public EzurisArchers copy() {
        return new EzurisArchers(this);
    }
}
