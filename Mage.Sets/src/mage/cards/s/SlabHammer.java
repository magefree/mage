
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SlabHammer extends CardImpl {

    public SlabHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, you may return a land you control to its owner's hand. If you do, the creature gets +2/+2 until end of turn.
        Ability ability = new AttacksAttachedTriggeredAbility(
                new DoIfCostPaid(new BoostEquippedEffect(2, 2, Duration.EndOfTurn),
                        new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(new FilterControlledLandPermanent())),
                        "Return a land you control to its owner's hand? (giving +2/+2 to the equipped creature)"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));

    }

    private SlabHammer(final SlabHammer card) {
        super(card);
    }

    @Override
    public SlabHammer copy() {
        return new SlabHammer(this);
    }
}
