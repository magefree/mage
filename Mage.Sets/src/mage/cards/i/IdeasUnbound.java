
package mage.cards.i;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class IdeasUnbound extends CardImpl {

    public IdeasUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");
        this.subtype.add(SubType.ARCANE);


        // Draw three cards. Discard three cards at the beginning of the next end step.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DiscardControllerEffect(3))));
    }

    private IdeasUnbound(final IdeasUnbound card) {
        super(card);
    }

    @Override
    public IdeasUnbound copy() {
        return new IdeasUnbound(this);
    }
}
