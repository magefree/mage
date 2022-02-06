

package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class BladedPinions extends CardImpl {

    public BladedPinions (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    public BladedPinions (final BladedPinions card) {
        super(card);
    }

    @Override
    public BladedPinions copy() {
        return new BladedPinions(this);
    }

}
