
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WakeTheDead extends CardImpl {

    public WakeTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");

        // Cast Wake the Dead only during combat on an opponent's turn.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, OnOpponentsTurnCondition.instance));

        // Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step.
        this.getSpellAbility().addEffect(new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().setTargetAdjuster(WakeTheDeadAdjuster.instance);
    }

    private WakeTheDead(final WakeTheDead card) {
        super(card);
    }

    @Override
    public WakeTheDead copy() {
        return new WakeTheDead(this);
    }
}

enum WakeTheDeadAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(ability.getManaCostsToPay().getX(), StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }
}

class WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step";
    }

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(final WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
    }

    @Override
    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
            controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
            List<Permanent> toSacrifice = new ArrayList<>(cards.size());
            for (UUID targetId : cards) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    toSacrifice.add(creature);
                }

            }
            Effect effect = new SacrificeTargetEffect("Sacrifice those creatures at the beginning of the next end step", source.getControllerId());
            effect.setTargetPointer(new FixedTargets(toSacrifice, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

}
