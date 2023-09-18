

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SwordOfVengeance extends CardImpl {

    public SwordOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);
        
        
        // Equipped creature gets +2/+0 and has first strike, vigilance, trample, and haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0));
        Effect effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has first strike");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", and haste");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    protected SwordOfVengeance(SwordOfVengeance me) {
        super(me);
    }

    @Override
    public SwordOfVengeance copy() {
        return new SwordOfVengeance(this);
    }

}
