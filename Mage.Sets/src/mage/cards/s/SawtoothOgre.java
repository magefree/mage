package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author arcox
 */
public final class SawtoothOgre extends CardImpl {

    public SawtoothOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sawtooth Ogre blocks or becomes blocked by a creature, Sawtooth Ogre deals 1 damage to that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DamageTargetEffect(1)), true);
        effect.setText("{this} deals 1 damage to that creature at end of combat");
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect));
    }

    private SawtoothOgre(final SawtoothOgre card) {
        super(card);
    }

    @Override
    public SawtoothOgre copy() {
        return new SawtoothOgre(this);
    }
}
