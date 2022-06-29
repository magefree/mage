
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class AshmouthBlade extends CardImpl {

    public AshmouthBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"");
        this.subtype.add(SubType.EQUIPMENT);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Equipped creature gets +3/+3
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 3));
        this.addAbility(ability);

        // and has first strike.
        Effect effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has first strike");
        ability.addEffect(effect);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private AshmouthBlade(final AshmouthBlade card) {
        super(card);
    }

    @Override
    public AshmouthBlade copy() {
        return new AshmouthBlade(this);
    }
}
