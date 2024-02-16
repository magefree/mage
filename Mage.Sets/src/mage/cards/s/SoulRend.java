
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SoulRend extends CardImpl {

    public SoulRend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Destroy target creature if it's white. A creature destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new SoulRendEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private SoulRend(final SoulRend card) {
        super(card);
    }

    @Override
    public SoulRend copy() {
        return new SoulRend(this);
    }
}

class SoulRendEffect extends OneShotEffect {
    SoulRendEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy target creature if it's white. A creature destroyed this way can't be regenerated";
    }

    private SoulRendEffect(final SoulRendEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && permanent.getColor(game).isWhite()) {
            permanent.destroy(source, game, true);
        }
        return false;
    }

    @Override
    public SoulRendEffect copy() {
        return new SoulRendEffect(this);
    }

}
