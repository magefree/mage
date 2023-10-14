
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ShallowGrave extends CardImpl {

    public ShallowGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Return the top creature card of your graveyard to the battlefield. That creature gains haste until end of turn. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ShallowGraveEffect());

    }

    private ShallowGrave(final ShallowGrave card) {
        super(card);
    }

    @Override
    public ShallowGrave copy() {
        return new ShallowGrave(this);
    }
}

class ShallowGraveEffect extends OneShotEffect {

    public ShallowGraveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return the top creature card of your graveyard to the battlefield. That creature gains haste until end of turn. Exile it at the beginning of the next end step";
    }

    private ShallowGraveEffect(final ShallowGraveEffect effect) {
        super(effect);
    }

    @Override
    public ShallowGraveEffect copy() {
        return new ShallowGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card lastCreatureCard = null;
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.isCreature(game)) {
                    lastCreatureCard = card;
                }
            }
            if (lastCreatureCard != null) {
                if (controller.moveCards(lastCreatureCard, Zone.BATTLEFIELD, source, game)) {
                    Permanent returnedCreature = game.getPermanent(lastCreatureCard.getId());
                    if (returnedCreature != null) {
                        FixedTarget fixedTarget = new FixedTarget(returnedCreature, game);
                        // Gains Haste
                        ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        hasteEffect.setTargetPointer(fixedTarget);
                        game.addEffect(hasteEffect, source);
                        // Exile it at end of turn
                        ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                        exileEffect.setTargetPointer(fixedTarget);
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
