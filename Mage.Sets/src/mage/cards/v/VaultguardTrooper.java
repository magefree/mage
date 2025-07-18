package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.TwoTappedCreaturesCondition;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VaultguardTrooper extends CardImpl {

    public VaultguardTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, if you control two or more tapped creatures, you may discard your hand. If you do, draw two cards.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardHandCost())
        ).withInterveningIf(TwoTappedCreaturesCondition.instance);
        this.addAbility(ability.addHint(TwoTappedCreaturesCondition.getHint()));
    }

    private VaultguardTrooper(final VaultguardTrooper card) {
        super(card);
    }

    @Override
    public VaultguardTrooper copy() {
        return new VaultguardTrooper(this);
    }
}