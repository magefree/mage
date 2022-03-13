
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class SurpriseDeployment extends CardImpl {

    public SurpriseDeployment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Cast Surprise Deployment only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // You may put a nonwhite creature card from your hand onto the battlefield. At the beginning of the next end step, return that creature to your hand. (Return it only if it's on the battlefield.)
        this.getSpellAbility().addEffect(new SurpriseDeploymentEffect());
    }

    private SurpriseDeployment(final SurpriseDeployment card) {
        super(card);
    }

    @Override
    public SurpriseDeployment copy() {
        return new SurpriseDeployment(this);
    }
}

class SurpriseDeploymentEffect extends OneShotEffect {

    private static final String choiceText = "Put a nonwhite creature card from your hand onto the battlefield?";
    
    private static final FilterCreatureCard filter = new FilterCreatureCard();
    
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public SurpriseDeploymentEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a nonwhite creature card from your hand onto the battlefield. At the beginning of the next end step, return that creature to your hand";
    }

    public SurpriseDeploymentEffect(final SurpriseDeploymentEffect effect) {
        super(effect);
    }

    @Override
    public SurpriseDeploymentEffect copy() {
        return new SurpriseDeploymentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                ReturnToHandTargetEffect effect = new ReturnToHandTargetEffect();
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                                game.addDelayedTriggeredAbility(delayedAbility, source);
                                return true;
                            }
                        }
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
