package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterBySubtypeCard;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author skaspels
 * Based on [Crown of Gondor][Dread Wanderer][Forerunner of the Coalition][Thirst for Discovery][Mask of Memory]
 */
public final class ArmMountedAnchor extends CardImpl {

    public ArmMountedAnchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has menace.
        Ability firstAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        firstAbility.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace. <i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(firstAbility);

        // Whenever equipped creature deals combat damage to a player, draw two cards. Then discard two cards unless you discard a Pirate card.
        Ability drawAbility = new DealsDamageToAPlayerAttachedTriggeredAbility(new DrawCardSourceControllerEffect(2), "equipped creature", false);
        DiscardCardCost cost = new DiscardCardCost(new FilterBySubtypeCard(SubType.PIRATE));
        cost.setText("Discard a Pirate card instead of discarding two cards");
        drawAbility.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard a Pirate card"));
        this.addAbility(drawAbility);

        // Equip {2}. This ability costs {2} less to activate if you have one or fewer cards in hand.
        EquipAbility eqAbility = new EquipAbility(2, false);
        eqAbility.setCostReduceText("This ability costs {2} less to activate if you have one or fewer cards in hand.");
        eqAbility.setCostAdjuster(ArmMountedAnchorAdjuster.instance);
        this.addAbility(eqAbility);
    }

    private ArmMountedAnchor(final ArmMountedAnchor card) {
        super(card);
    }

    @Override
    public ArmMountedAnchor copy() {
        return new ArmMountedAnchor(this);
    }
}

enum ArmMountedAnchorAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        // checking state
        if (HeckbentCondition.instance.apply(game, ability)) {
            CardUtil.reduceCost(ability, 2);
        }

    }
}
