package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class SkewerSlinger extends CardImpl {

    public SkewerSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Skewer Slinger blocks or becomes blocked by a creature, Skewer Slinger deals 1 damage to that creature.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new DamageTargetEffect(1, true, "that creature")
        ));
    }

    private SkewerSlinger(final SkewerSlinger card) {
        super(card);
    }

    @Override
    public SkewerSlinger copy() {
        return new SkewerSlinger(this);
    }
}
