package mage.cards.f;

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
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FireBowman extends CardImpl {

    public FireBowman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Fire Bowman: Fire Bowman deals 1 damage to any target. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1, "it"),
                new SacrificeSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FireBowman(final FireBowman card) {
        super(card);
    }

    @Override
    public FireBowman copy() {
        return new FireBowman(this);
    }
}
