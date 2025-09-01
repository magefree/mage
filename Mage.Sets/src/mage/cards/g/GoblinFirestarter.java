package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GoblinFirestarter extends CardImpl {

    public GoblinFirestarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Goblin Firestarter: Goblin Firestarter deals 1 damage to any target. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DamageTargetEffect(1, "it"),
                new SacrificeSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinFirestarter(final GoblinFirestarter card) {
        super(card);
    }

    @Override
    public GoblinFirestarter copy() {
        return new GoblinFirestarter(this);
    }
}
