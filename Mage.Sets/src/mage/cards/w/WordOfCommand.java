
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class WordOfCommand extends CardImpl {

    public WordOfCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");

        // Look at target opponent's hand and choose a card from it. You control that player until Word of Command finishes resolving. The player plays that card if able. While doing so, the player can activate mana abilities only if they're from lands that player controls and only if mana they produce is spent to activate other mana abilities of lands the player controls and/or to play that card. If the chosen card is cast as a spell, you control the player while that spell is resolving.
        this.getSpellAbility().addEffect(new WordOfCommandEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public WordOfCommand(final WordOfCommand card) {
        super(card);
    }

    @Override
    public WordOfCommand copy() {
        return new WordOfCommand(this);
    }
}

class WordOfCommandEffect extends OneShotEffect {

    public WordOfCommandEffect() {
        super(Outcome.GainControl);
        this.staticText = "Look at target opponent's hand and choose a card from it. You control that player until Word of Command finishes resolving. The player plays that card if able. While doing so, the player can activate mana abilities only if they're from lands that player controls and only if mana they produce is spent to activate other mana abilities of lands the player controls and/or to play that card. If the chosen card is cast as a spell, you control the player while that spell is resolving";
    }

    public WordOfCommandEffect(final WordOfCommandEffect effect) {
        super(effect);
    }

    @Override
    public WordOfCommandEffect copy() {
        return new WordOfCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card card = null;
        if (sourceController != null && targetPlayer != null && sourceObject != null) {
            Player controller = null;
            Spell wordOfCommand = game.getSpell(source.getSourceId());
            if (wordOfCommand != null) {
                if (wordOfCommand.getCommandedBy() != null) {
                    controller = game.getPlayer(wordOfCommand.getCommandedBy());
                } else {
                    controller = game.getPlayer(sourceController.getTurnControlledBy());
                }
            }
            if (controller == null) {
                controller = sourceController; // reset the controller to avoid NPE
            }

            // Look at target opponent's hand and choose a card from it
            TargetCard targetCard = new TargetCard(Zone.HAND, new FilterCard());
            if (controller.choose(Outcome.Discard, targetPlayer.getHand(), targetCard, game)) {
                card = game.getCard(targetCard.getFirstTarget());
            }

            // You control that player until Word of Command finishes resolving
            controller.controlPlayersTurn(game, targetPlayer.getId());
            while (controller.canRespond()) {
                if (controller.chooseUse(Outcome.Benefit, "Resolve " + sourceObject.getLogName() + " now" + (card != null ? " and play " + card.getLogName() : "") + '?', source, game)) {
                    // this is used to give the controller a little space to utilize his player controlling effect (look at face down creatures, hand, etc.)
                    break;
                }
            }

            // The player plays that card if able
            if (card != null) {
                // While doing so, the player can activate mana abilities only if they're from lands that player controls
                RestrictionEffect effect = new WordOfCommandCantActivateEffect();
                effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                game.addEffect(effect, source);

                // and only if mana they produce is spent to activate other mana abilities of lands he or she controls and/or play that card
                ManaPool manaPool = targetPlayer.getManaPool();
                manaPool.setForcedToPay(true);
                manaPool.storeMana();
                int bookmark = game.bookmarkState();

                boolean canPlay = checkPlayability(card, targetPlayer, game, source);
                while (canPlay
                        && targetPlayer.canRespond()
                        && !targetPlayer.playCard(card, game, false, true, new MageObjectReference(source.getSourceObject(game), game))) {
                    SpellAbility spellAbility = card.getSpellAbility();
                    if (spellAbility != null) {
                        spellAbility.getManaCostsToPay().clear();
                        spellAbility.getManaCostsToPay().addAll(spellAbility.getManaCosts());
                        ((ManaCostsImpl) spellAbility.getManaCostsToPay()).forceManaRollback(game, manaPool); // force rollback if card was deemed playable
                        canPlay = checkPlayability(card, targetPlayer, game, source);
                    } else {
                        break;
                    }
                }
                if (!canPlay) {
                    game.informPlayers(targetPlayer.getLogName() + " didn't play " + card.getLogName() + " (card can't be played)");
                }

                manaPool.setForcedToPay(false); // duplicate in case of a new mana pool existing - probably not necessary, but just in case
                manaPool = targetPlayer.getManaPool(); // a rollback creates a new mana pool for the player, so it's necessary to find it again
                manaPool.setForcedToPay(false);
                game.removeBookmark(bookmark);
                targetPlayer.resetStoredBookmark(game);
                for (RestrictionEffect eff : game.getContinuousEffects().getRestrictionEffects()) {
                    if (eff instanceof WordOfCommandCantActivateEffect) {
                        eff.discard();
                        break;
                    }
                }
                game.getContinuousEffects().removeInactiveEffects(game);
                Spell spell = game.getSpell(card.getId());
                if (spell != null) {
                    spell.setCommandedBy(controller.getId()); // If the chosen card is cast as a spell, you control the player while that spell is resolving
                }
            }

            wordOfCommand = game.getSpell(source.getSourceId());
            if (wordOfCommand != null) {
                wordOfCommand.setCommandedBy(controller.getId()); // You control the player until Word of Command finishes resolving
            } else {
                targetPlayer.setGameUnderYourControl(true, false);
                if (!targetPlayer.getTurnControlledBy().equals(controller.getId())) {
                    controller.getPlayersUnderYourControl().remove(targetPlayer.getId());
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkPlayability(Card card, Player targetPlayer, Game game, Ability source) {
        // check for card playability
        boolean canPlay = false;
        if (card.isLand()) { // we can't use getPlayableInHand(game) in here because it disallows playing lands outside the main step
            if (targetPlayer.canPlayLand()
                    && game.getActivePlayerId().equals(targetPlayer.getId())) {
                canPlay = true;
                for (Ability ability : card.getAbilities(game)) {
                    if (!game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, ability.getSourceId(), ability.getSourceId(), targetPlayer.getId()), ability, game, true)) {
                        canPlay &= true;
                    }
                }
            }
        } else { // Word of Command allows the chosen card to be played "as if it had flash" so we need to invoke such effect to bypass the check
            AsThoughEffectImpl effect2 = new WordOfCommandTestFlashEffect();
            game.addEffect(effect2, source);
            if (targetPlayer.getPlayableInHand(game).contains(card.getId())) {
                canPlay = true;
            }
            for (AsThoughEffect eff : game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.CAST_AS_INSTANT, game)) {
                if (eff instanceof WordOfCommandTestFlashEffect) {
                    eff.discard();
                    break;
                }
            }
        }
        return canPlay;
    }
}

class WordOfCommandCantActivateEffect extends RestrictionEffect {

    public WordOfCommandCantActivateEffect() {
        super(Duration.EndOfTurn);
    }

    public WordOfCommandCantActivateEffect(final WordOfCommandCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public WordOfCommandCantActivateEffect copy() {
        return new WordOfCommandCantActivateEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return !permanent.isLand() && permanent.getControllerId().equals(this.targetPointer.getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }
}

class WordOfCommandTestFlashEffect extends AsThoughEffectImpl {

    public WordOfCommandTestFlashEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
    }

    public WordOfCommandTestFlashEffect(final WordOfCommandTestFlashEffect effect) {
        super(effect);
    }

    @Override
    public WordOfCommandTestFlashEffect copy() {
        return new WordOfCommandTestFlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID affectedSpellId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
