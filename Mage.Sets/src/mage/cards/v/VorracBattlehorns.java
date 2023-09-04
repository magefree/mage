
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class VorracBattlehorns extends CardImpl {

    public VorracBattlehorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has trample and can't be blocked by more than one creature.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.EQUIPMENT, 1);
        effect.setText("and can't be blocked by more than one creature");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private VorracBattlehorns(final VorracBattlehorns card) {
        super(card);
    }

    @Override
    public VorracBattlehorns copy() {
        return new VorracBattlehorns(this);
    }
}