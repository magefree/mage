package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class GorgonFlail extends CardImpl {

    public GorgonFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has deathtouch"));

        this.addAbility(new EquipAbility(2));
    }

    private GorgonFlail(final GorgonFlail card) {
        super(card);
    }

    @Override
    public GorgonFlail copy() {
        return new GorgonFlail(this);
    }
}
