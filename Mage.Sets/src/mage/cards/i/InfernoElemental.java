package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class InfernoElemental extends CardImpl {

    public InfernoElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Inferno Elemental blocks or becomes blocked by a creature, Inferno Elemental deals 3 damage to that creature.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(new DamageTargetEffect(3, true, "that creature")));
    }

    private InfernoElemental(final InfernoElemental card) {
        super(card);
    }

    @Override
    public InfernoElemental copy() {
        return new InfernoElemental(this);
    }
}
