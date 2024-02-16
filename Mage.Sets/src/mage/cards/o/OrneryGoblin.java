package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class OrneryGoblin extends CardImpl {

    public OrneryGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ornery Goblin blocks or becomes blocked by a creature, Ornery Goblin deals 1 damage to that creature.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(new DamageTargetEffect(1, true, "that creature")));
    }

    private OrneryGoblin(final OrneryGoblin card) {
        super(card);
    }

    @Override
    public OrneryGoblin copy() {
        return new OrneryGoblin(this);
    }
}
