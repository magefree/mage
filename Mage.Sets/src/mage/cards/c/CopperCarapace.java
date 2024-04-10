package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;

/**
 *
 * @author North
 */
public final class CopperCarapace extends CardImpl {

    public CopperCarapace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));

        // Equipped creature gets +2/+2 and can't block.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new CantBlockAttachedEffect(AttachmentType.EQUIPMENT).setText("and can't block"));
        this.addAbility(ability);
    }

    private CopperCarapace(final CopperCarapace card) {
        super(card);
    }

    @Override
    public CopperCarapace copy() {
        return new CopperCarapace(this);
    }
}
