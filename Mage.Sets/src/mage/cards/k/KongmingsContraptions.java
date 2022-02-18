package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author L_J
 */
public final class KongmingsContraptions extends CardImpl {

    public KongmingsContraptions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Kongming's Contraptions deals 2 damage to target attacking creature. Activate this ability only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost(), 
                new CompoundCondition("during the declare attackers step and only if you've been attacked this step", 
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false), AttackedThisStepCondition.instance)
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability, new PlayerAttackedStepWatcher());
    }

    private KongmingsContraptions(final KongmingsContraptions card) {
        super(card);
    }

    @Override
    public KongmingsContraptions copy() {
        return new KongmingsContraptions(this);
    }
}
