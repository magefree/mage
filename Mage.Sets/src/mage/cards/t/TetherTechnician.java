package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TetherTechnician extends CardImpl {

    public TetherTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, you may discard a card. When you do, this creature deals 2 damage to any target.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
            new DamageTargetEffect(2), false
        );
        reflexiveAbility.addTarget(new TargetAnyTarget());
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new DoWhenCostPaid(reflexiveAbility, new DiscardCardCost(), "Discard a card?")
        );
        this.addAbility(ability);
    }

    private TetherTechnician(final TetherTechnician card) {
        super(card);
    }

    @Override
    public TetherTechnician copy() {
        return new TetherTechnician(this);
    }
}
