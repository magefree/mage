package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PlaneswalkersMischief extends CardImpl {

    public PlaneswalkersMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // {3}{U}: Target opponent reveals a card at random from their hand. If it's an instant or sorcery card, exile it. You may cast it without paying its mana cost for as long as it remains exiled. At the beginning of the next end step, if you haven't cast it, return it to its owner's hand. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersMischiefEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private PlaneswalkersMischief(final PlaneswalkersMischief card) {
        super(card);
    }

    @Override
    public PlaneswalkersMischief copy() {
        return new PlaneswalkersMischief(this);
    }
}

class PlaneswalkersMischiefEffect extends OneShotEffect {

    public PlaneswalkersMischiefEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals a card at random from their hand. If it's an instant or sorcery card, exile it. You may cast it without paying its mana cost for as long as it remains exiled. At the beginning of the next end step, if you haven't cast it, return it to its owner's hand.";
    }

    public PlaneswalkersMischiefEffect(final PlaneswalkersMischiefEffect effect) {
        super(effect);
    }

    @Override
    public PlaneswalkersMischiefEffect copy() {
        return new PlaneswalkersMischiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null && opponent.getHand().size() > 0) {
            Card revealedCard = opponent.getHand().getRandom(game);
            if (revealedCard == null) {
                return false;
            }
            Cards cards = new CardsImpl(revealedCard);
            opponent.revealCards(source, cards, game);
            if (revealedCard.isInstant(game)
                    || revealedCard.isSorcery(game)) {
                opponent.moveCardToExileWithInfo(revealedCard, source.getSourceId(), "Planeswalker's Mischief", source, game, Zone.HAND, true);
                AsThoughEffect effect = new PlaneswalkersMischiefCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(revealedCard.getId()));
                game.addEffect(effect, source);
                OneShotEffect effect2 = new ReturnFromExileEffect(Zone.HAND);
                Condition condition = new PlaneswalkersMischiefCondition(source.getSourceId(), revealedCard.getId());
                ConditionalOneShotEffect effect3 = new ConditionalOneShotEffect(effect2, condition, "if you haven't cast it, return it to its owner's hand.");
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect3);
                delayedAbility.addWatcher(new SpellsCastWatcher());
                game.addDelayedTriggeredAbility(delayedAbility, source);
                return true;
            }
        }
        return false;
    }
}

class PlaneswalkersMischiefCastFromExileEffect extends AsThoughEffectImpl {

    PlaneswalkersMischiefCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card without paying its mana cost as long as it remains exiled";
    }

    PlaneswalkersMischiefCastFromExileEffect(final PlaneswalkersMischiefCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlaneswalkersMischiefCastFromExileEffect copy() {
        return new PlaneswalkersMischiefCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(objectId)
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player player = game.getPlayer(source.getControllerId());
            Card card = game.getCard(objectId);
            if (player != null
                    && card != null) {
                return allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
            }
        }
        return false;
    }
}

class PlaneswalkersMischiefCondition implements Condition {

    protected UUID exileId;
    protected UUID cardId;

    public PlaneswalkersMischiefCondition(UUID exileId, UUID cardId) {
        this.exileId = exileId;
        this.cardId = cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getExile().getExileZone(exileId).contains(cardId)) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
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
