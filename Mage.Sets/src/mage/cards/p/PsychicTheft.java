
package mage.cards.p;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author L_J (significantly based on code by jeffwadsworth and Styxo)
 */
public final class PsychicTheft extends CardImpl {

    public PsychicTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target player reveals their hand. You choose an instant or sorcery card from it and exile that card. You may cast that card for as long as it remains exiled. At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PsychicTheftEffect());

    }

    public PsychicTheft(final PsychicTheft card) {
        super(card);
    }

    @Override
    public PsychicTheft copy() {
        return new PsychicTheft(this);
    }
}

class PsychicTheftEffect extends OneShotEffect {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    public PsychicTheftEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals their hand. You choose an instant or sorcery card from it and exile that card. You may cast that card for as long as it remains exiled. At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.";
    }

    public PsychicTheftEffect(final PsychicTheftEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTheftEffect copy() {
        return new PsychicTheftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (opponent != null && sourceObject != null) {
            opponent.revealCards(sourceObject.getName(), opponent.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int cardsHand = opponent.getHand().count(filter, game);
                Card chosenCard = null;
                if (cardsHand > 0) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    if (controller.choose(Outcome.Benefit, opponent.getHand(), target, game)) {
                        chosenCard = opponent.getHand().get(target.getFirstTarget(), game);
                    }
                }
                if (chosenCard != null) {

                    opponent.moveCardToExileWithInfo(chosenCard, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.HAND, true);

                    AsThoughEffect effect = new PsychicTheftCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(chosenCard.getId()));
                    game.addEffect(effect, source);

                    OneShotEffect effect2 = new ReturnFromExileEffect(source.getSourceId(), Zone.HAND);
                    Condition condition = new PsychicTheftCondition(source.getSourceId(), chosenCard.getId());

                    ConditionalOneShotEffect effect3 = new ConditionalOneShotEffect(effect2, condition, "if you haven't cast it, return it to its owner's hand.");
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect3);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class PsychicTheftCastFromExileEffect extends AsThoughEffectImpl {

    PsychicTheftCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled";
    }

    PsychicTheftCastFromExileEffect(final PsychicTheftCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PsychicTheftCastFromExileEffect copy() {
        return new PsychicTheftCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(objectId)
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player player = game.getPlayer(source.getControllerId());
            Card card = game.getCard(objectId);
            if (player != null
                    && card != null) {
                return true;
            }
        }
        return false;
    }
}

class PsychicTheftCondition implements Condition {

    protected UUID exileId;
    protected UUID cardId;

    public PsychicTheftCondition(UUID exileId, UUID cardId) {
        this.exileId = exileId;
        this.cardId = cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getExile().getExileZone(exileId).contains(cardId)) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class, source.getSourceId());
        if (watcher != null) {
            List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spells != null) {
                for (Spell spell : spells) {
                    if (spell.getSourceId().equals(cardId)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
