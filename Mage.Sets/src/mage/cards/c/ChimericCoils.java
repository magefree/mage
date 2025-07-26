package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BecomesXXConstructSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ChimericCoils extends CardImpl {

    public ChimericCoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {X}{1}: Chimeric Coils becomes an X/X Construct artifact creature. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new BecomesXXConstructSourceEffect(Duration.Custom), new ManaCostsImpl<>("{X}{1}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())));
        this.addAbility(ability);
    }

    private ChimericCoils(final ChimericCoils card) {
        super(card);
    }

    @Override
    public ChimericCoils copy() {
        return new ChimericCoils(this);
    }
}
