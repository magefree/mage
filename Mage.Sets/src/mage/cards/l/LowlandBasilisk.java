
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class LowlandBasilisk extends CardImpl {

    public LowlandBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Lowland Basilisk deals damage to a creature, destroy that creature at end of combat.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect("destroy that creature at end of combat")), true),
                false, 
                false,
                true));
    }

    private LowlandBasilisk(final LowlandBasilisk card) {
        super(card);
    }

    @Override
    public LowlandBasilisk copy() {
        return new LowlandBasilisk(this);
    }
}
