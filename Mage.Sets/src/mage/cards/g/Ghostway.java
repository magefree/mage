package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Ghostway extends CardImpl {

    public Ghostway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Exile each creature you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GhostwayEffect());
    }

    private Ghostway(final Ghostway card) {
        super(card);
    }

    @Override
    public Ghostway copy() {
        return new Ghostway(this);
    }
}

class GhostwayEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("each creature you control");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GhostwayEffect() {
        super(Outcome.Neutral);
        staticText = "Exile each creature you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    private GhostwayEffect(final GhostwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Set<Card> toExile = new HashSet<>();
            toExile.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game));
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            controller.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());

            Cards cardsToReturn = new CardsImpl();
            for (Card exiled : toExile) {
                if (exiled.getZoneChangeCounter(game) == game.getState().getZoneChangeCounter(exiled.getId()) - 1) {
                    cardsToReturn.add(exiled);
                }
            }
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
            effect.setTargetPointer(new FixedTargets(cardsToReturn, game));
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

    @Override
    public GhostwayEffect copy() {
        return new GhostwayEffect(this);
    }
}
