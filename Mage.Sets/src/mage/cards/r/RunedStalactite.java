
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class RunedStalactite extends CardImpl {

    public RunedStalactite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and is every creature type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1,1)));
        Effect effect = new GainAbilityAttachedEffect(ChangelingAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield);
        effect.setText("Equipped creature is every creature type (Changeling)");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl("{2}")));
    }

    public RunedStalactite(final RunedStalactite card) {
        super(card);
    }

    @Override
    public RunedStalactite copy() {
        return new RunedStalactite(this);
    }
}
