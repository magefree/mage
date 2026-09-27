package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.constants.SubType;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author muz
 */
public final class LichsRelic extends CardImpl {

    public LichsRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, you may pay {2}. When you do, for each opponent, destroy up to one target creature or planeswalker that player controls.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new DestroyTargetEffect().setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, destroy up to one target creature or planeswalker that player controls"),
            false
        );
        reflexive.addTarget(new TargetCreatureOrPlaneswalker(0, 1));
        reflexive.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoWhenCostPaid(reflexive, new GenericManaCost(2), "Pay {2}?")
        ));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private LichsRelic(final LichsRelic card) {
        super(card);
    }

    @Override
    public LichsRelic copy() {
        return new LichsRelic(this);
    }
}
