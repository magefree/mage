package mage.cards.d;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Drossclaw extends CardImpl {

    public Drossclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature attacks, each opponent loses 1 life.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new LoseLifeOpponentsEffect(1), AttachmentType.EQUIPMENT, false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private Drossclaw(final Drossclaw card) {
        super(card);
    }

    @Override
    public Drossclaw copy() {
        return new Drossclaw(this);
    }
}
