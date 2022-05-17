
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedHasSupertypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class ChampionsHelm extends CardImpl {

    private static final String staticText = "As long as equipped creature is legendary, it has hexproof";
    
    public ChampionsHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        
        // As long as equipped creature is legendary, it has hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.EQUIPMENT), 
                new EquippedHasSupertypeCondition(SuperType.LEGENDARY), staticText)));
        
        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private ChampionsHelm(final ChampionsHelm card) {
        super(card);
    }

    @Override
    public ChampionsHelm copy() {
        return new ChampionsHelm(this);
    }
}
