package mage.cards.s;

import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.ProtectionChosenColorAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

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
        this.addAbility(new AttachedToCreatureSourceTriggeredAbility(new ChooseColorEffect(Outcome.Benefit), false));

        // Equipped creature gets +2/+0 and has protection from the last chosen color.
        Effect boostEffect = new BoostEquippedEffect(2, 0);
        boostEffect.concatBy(".");
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, boostEffect);
        ProtectionChosenColorAttachedEffect protectionEfect = new ProtectionChosenColorAttachedEffect(false);
        protectionEfect.setText("and has protection from the last chosen color.");
        ability.addEffect(protectionEfect);
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private SanctuaryBlade(final SanctuaryBlade card) {
        super(card);
    }

    @Override
    public SanctuaryBlade copy() {
        return new SanctuaryBlade(this);
    }
}
