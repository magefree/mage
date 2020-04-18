package mage.cards.s;

import mage.abilities.common.AttachedToCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctuaryBlade extends CardImpl {

    public SanctuaryBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // As Sanctuary Blade becomes attached to a creature, choose a color.
        GainProtectionFromColorAttachedEffect protectionEffect = new GainProtectionFromColorAttachedEffect(Duration.WhileOnBattlefield);
        this.addAbility(new AttachedToCreatureTriggeredAbility(protectionEffect, false));

        // Equipped creature gets +2/+0 and has protection from the last chosen color.
        Effect boostEffect = new BoostEquippedEffect(2, 0);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, boostEffect));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    private SanctuaryBlade(final SanctuaryBlade card) {
        super(card);
    }

    @Override
    public SanctuaryBlade copy() {
        return new SanctuaryBlade(this);
    }
}
