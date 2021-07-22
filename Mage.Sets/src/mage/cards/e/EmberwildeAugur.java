
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class EmberwildeAugur extends CardImpl {

    public EmberwildeAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice Emberwilde Augur: Emberwilde Augur deals 3 damage to target player. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(3, "it"),
                new SacrificeSourceCost(),
                new IsStepCondition(PhaseStep.UPKEEP));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private EmberwildeAugur(final EmberwildeAugur card) {
        super(card);
    }

    @Override
    public EmberwildeAugur copy() {
        return new EmberwildeAugur(this);
    }
}
