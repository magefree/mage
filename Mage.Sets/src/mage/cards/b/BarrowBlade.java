package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.BlocksOrBlockedAttachedTriggeredAbility;
import mage.abilities.common.BlocksOrBlockedByCreatureAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 * @author TiagoMDG
 */
public final class BarrowBlade extends CardImpl {

    public BarrowBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature blocks or becomes blocked by a creature, that creature loses all abilities until end of turn.
        this.addAbility(new BlocksOrBlockedByCreatureAttachedTriggeredAbility(
                new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                        .setText("that creature loses all abilities " +
                                "until end of turn."), AttachmentType.EQUIPMENT, false));


        // Equip {1}
        this.addAbility(new EquipAbility(1, true));
    }

    private BarrowBlade(final BarrowBlade card) {
        super(card);
    }

    @Override
    public BarrowBlade copy() {
        return new BarrowBlade(this);
    }
}