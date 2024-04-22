package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SandsOfDelirium extends CardImpl {

    public SandsOfDelirium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {X}, {tap}: Target player puts the top X cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(ManacostVariableValue.REGULAR), new VariableManaCost(VariableCostType.NORMAL));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SandsOfDelirium(final SandsOfDelirium card) {
        super(card);
    }

    @Override
    public SandsOfDelirium copy() {
        return new SandsOfDelirium(this);
    }
}
