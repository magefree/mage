package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class AccordersShield extends CardImpl {

    public AccordersShield (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");
        this.subtype.add(SubType.EQUIPMENT);

        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 3));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has vigilance"));
        this.addAbility(ability);

        this.addAbility(new EquipAbility(3));

    }

    public AccordersShield (final AccordersShield card) {
        super(card);
    }

    @Override
    public AccordersShield copy() {
        return new AccordersShield(this);
    }

}
