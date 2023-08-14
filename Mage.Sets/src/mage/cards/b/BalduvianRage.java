
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetCreaturePermanent;


/**
 * @author duncant
 */
public final class BalduvianRage extends CardImpl {

    public BalduvianRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");

        // Target attacking creature gets +X/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)),false
        ).concatBy("<br>"));
    }

    private BalduvianRage(final BalduvianRage card) {
        super(card);
    }

    @Override
    public BalduvianRage copy() {
        return new BalduvianRage(this);
    }
}
