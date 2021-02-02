
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author MarcoMarin
 */
public final class Rakalite extends CardImpl {

    public Rakalite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {2}: Prevent the next 1 damage that would be dealt to any target this turn. Return Rakalite to its owner's hand at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1, false), new GenericManaCost(2));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandSourceEffect(true))));

        this.addAbility(ability);
    }

    private Rakalite(final Rakalite card) {
        super(card);
    }

    @Override
    public Rakalite copy() {
        return new Rakalite(this);
    }
}
