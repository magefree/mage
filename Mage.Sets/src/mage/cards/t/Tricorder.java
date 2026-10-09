package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Tricorder extends CardImpl {

    public Tricorder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature attacks, surveil 1.
        this.addAbility(new AttacksAttachedTriggeredAbility(new SurveilEffect(1)));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private Tricorder(final Tricorder card) {
        super(card);
    }

    @Override
    public Tricorder copy() {
        return new Tricorder(this);
    }
}
