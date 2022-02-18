
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.ScryEffect;
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
public final class WitchesEye extends CardImpl {

    public WitchesEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{1}, {T}: Scry 1."</i>
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1, false), new GenericManaCost(1));
        gainedAbility.addCost(new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield)));
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(1)));
    }

    private WitchesEye(final WitchesEye card) {
        super(card);
    }

    @Override
    public WitchesEye copy() {
        return new WitchesEye(this);
    }
}
