package mage.abilities.keyword;

import mage.Constants;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.WatcherImpl;

import java.util.UUID;

/**
 * 702.33. Madness
 *
 * 702.33a. Madness is a keyword that represents two abilities.
 *
 * The first is a static ability that functions while the card with madness is in a player's hand.
 * The second is a triggered ability that functions when the first ability is applied.
 *
 * "Madness [cost]" means "If a player would discard this card, that player discards it, but may exile it instead of putting it into his or her graveyard" and
 * "When this card is exiled this way, its owner may cast it by paying [cost] rather than paying its mana cost.
 * If that player doesn't, he or she puts this card into his or her graveyard.
 *
 * 702.33b. Casting a spell using its madness ability follows the rules for paying alternative costs in rules 601.2b and 601.2e-g.
 *
 * @author magenoxx_at_gmail.com
 */
public class MadnessAbility extends StaticAbility<MadnessAbility> {

    private Cost madnessCost;
    
    public MadnessAbility(Card card, Cost cost) {
        super(Constants.Zone.STACK, null);
        this.madnessCost = cost;
        card.addAbility(new SimpleStaticAbility(Constants.Zone.EXILED, new MadnessPlayEffect(cost)));
        card.addAbility(new MadnessTriggeredAbility());
        card.addWatcher(new MadnessCleanUpWatcher());
    }

    public MadnessAbility(final MadnessAbility ability) {
        super(ability);
        this.madnessCost = ability.madnessCost;
    }

    @Override
    public MadnessAbility copy() {
        return new MadnessAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Madness ");
        sbRule.append(madnessCost.getText());
        return sbRule.toString();
    }
}

/**
 *
 * This effect asks player about exiled card to be cast by its madness cost.
 * It checks:
 * 1. That card is in Exile zone
 * 2. It is being cast by owner
 * 3. It been discarded so it contains 'madness' mark stored in game state
 *
 */
class MadnessPlayEffect extends AsThoughEffectImpl<MadnessPlayEffect> {

    private Cost cost;

    public MadnessPlayEffect(Cost cost) {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfGame, Constants.Outcome.Benefit);
        staticText = null;
        this.cost = cost;
    }

    public MadnessPlayEffect(final MadnessPlayEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MadnessPlayEffect copy() {
        return new MadnessPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && card.getOwnerId().equals(source.getControllerId()) && game.getState().getZone(source.getSourceId()) == Constants.Zone.EXILED) {
                Object object = game.getState().getValue("madness_" + card.getId());
                if (object != null && object.equals(true)) {
                    Player player = game.getPlayer(card.getOwnerId());
                    String message = "Cast " + card.getName() + " by its madness cost?";
                    if (player != null && player.chooseUse(Constants.Outcome.Benefit, message, game)) {
                        if (cost.pay(card.getSpellAbility(), game, sourceId, player.getId(), false)) {
                            card.getSpellAbility().getManaCostsToPay().clear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

/**
 *
 * This triggered ability along with effect exiles card with Madness abilities whenever it gets discarded.
 * It also marks it with "madness" mark storing unique value to game state
 * that will be used to check that card can be cast from exile zone.
 */
class MadnessTriggeredAbility extends TriggeredAbilityImpl<MadnessTriggeredAbility> {

    MadnessTriggeredAbility() {
        super(Constants.Zone.GRAVEYARD, new MadnessExileEffect(), true);
    }

    MadnessTriggeredAbility(final MadnessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MadnessTriggeredAbility copy() {
        return new MadnessTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            MageObject mageObject = game.getCard(event.getTargetId());
            if (mageObject != null && this.getSourceId().equals(mageObject.getId()) && mageObject instanceof Card) {
                Card card = (Card) mageObject;
                Player controller = game.getPlayer(event.getPlayerId());
                if (controller != null) {
                    for (Ability madness : card.getAbilities()) {
                        if (madness instanceof MadnessAbility) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard";
    }
}

class MadnessExileEffect extends OneShotEffect<MadnessExileEffect> {

    public MadnessExileEffect() {
        super(Constants.Outcome.Benefit);
    }

    public MadnessExileEffect(final MadnessExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player != null && card != null) {
            Object object = game.getState().getValue("madness exile");
            if (object == null || !(object instanceof UUID)) {
                object = UUID.randomUUID();
                game.getState().setValue("madness exile", object);
            }
            card.moveToExile((UUID)object, "Madness", card.getId(), game);
            card.addInfo("madness", "<i>This card is being cast using madness cost</i>");
            game.getState().setValue("madness_" + card.getId(), true);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return null;
    }

    @Override
    public MadnessExileEffect copy() {
        return new MadnessExileEffect(this);
    }
}

/**
 * Whenever phase is changed, this watcher returns all cards exiled by madness to graveyard and informs players about it.
 */
class MadnessCleanUpWatcher extends WatcherImpl<MadnessCleanUpWatcher> {

    public MadnessCleanUpWatcher() {
        super("MadnessPlayWasCanceled", Constants.WatcherScope.GAME);
    }

    public MadnessCleanUpWatcher(final MadnessCleanUpWatcher watcher) {
        super(watcher);
    }

    @Override
    public MadnessCleanUpWatcher copy() {
        return new MadnessCleanUpWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PHASE_CHANGED) {
            for (Card card : game.getExile().getAllCards(game)) {
                Object object = game.getState().getValue("madness_" + card.getId());
                if (object != null && object.equals(true)) {
                    game.informPlayers("Madness cost wasn't paied. " + card.getName() + " was put to its owner's graveyard.");
                    // reset
                    game.getState().setValue("madness_" + card.getId(), null);
                }
                card.moveToZone(Constants.Zone.GRAVEYARD, sourceId, game, true);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}