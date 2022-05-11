
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author emerald000
 */
public final class BrawlersPlate extends CardImpl {

    public BrawlersPlate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has trample
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has trample"));
        this.addAbility(ability);
        
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));
    }

    private BrawlersPlate(final BrawlersPlate card) {
        super(card);
    }

    @Override
    public BrawlersPlate copy() {
        return new BrawlersPlate(this);
    }
}
