package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class SpikedRipsaw extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOREST, "forest");

    public SpikedRipsaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 3)));

        // Whenever equipped creature attacks, you may sacrifice a Forest. If you do, that creature gains trample until end of turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new DoIfCostPaid(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, "that creature gains trample until end of turn"), new SacrificeTargetCost(filter)),
                AttachmentType.EQUIPMENT, false, true
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private SpikedRipsaw(final SpikedRipsaw card) {
        super(card);
    }

    @Override
    public SpikedRipsaw copy() {
        return new SpikedRipsaw(this);
    }
}
