
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ExchangeLifeControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class MagusOfTheMirror extends CardImpl {

    public MagusOfTheMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice Magus of the Mirror: Exchange life totals with target opponent. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new ExchangeLifeControllerTargetEffect(),
                new TapSourceCost(),
                new IsStepCondition(PhaseStep.UPKEEP)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MagusOfTheMirror(final MagusOfTheMirror card) {
        super(card);
    }

    @Override
    public MagusOfTheMirror copy() {
        return new MagusOfTheMirror(this);
    }
}
