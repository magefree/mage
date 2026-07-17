package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetAndTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class AssembledAlphas extends CardImpl {

    public AssembledAlphas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Assembled Alphas blocks or becomes blocked by a creature, Assembled Alphas deals 3 damage to that creature and 3 damage to that creature's controller.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new DamageTargetAndTargetControllerEffect(3, 3)));
    }

    private AssembledAlphas(final AssembledAlphas card) {
        super(card);
    }

    @Override
    public AssembledAlphas copy() {
        return new AssembledAlphas(this);
    }
}
