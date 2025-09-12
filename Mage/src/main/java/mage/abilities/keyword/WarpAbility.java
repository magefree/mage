package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * 702.185. Warp
 * <p>
 * 702.185a Warp represents two static abilities that function while the card with warp is on the stack,
 * one of which may create a delayed triggered ability. “Warp [cost]” means
 * “You may cast this card from your hand by paying [cost] rather than its mana cost” and
 * “If this spell’s warp cost was paid, exile the permanent this spell becomes at the beginning of the next end step.
 * Its owner may cast this card after the current turn has ended for as long as it remains exiled.”
 * Casting a spell for its warp cost follows the rules for paying alternative costs in rules 601.2b and 601.2f–h.
 * <p>
 * 702.185b Some effects refer to “warped” cards in exile. A warped card in exile is one
 * that was exiled by the delayed triggered ability created by a warp ability.
 * <p>
 * 702.185c Some effects refer to whether “a spell was warped this turn.”
 * This means that a spell was cast for its warp cost this turn.
 *
 * @author TheElk801
 */
public class WarpAbility extends SpellAbility {

    public static final String WARP_ACTIVATION_VALUE_KEY = "warpActivation";
    private final boolean allowGraveyard;

    public WarpAbility(Card card, String manaString) {
        this(card, manaString, false);
    }

    public WarpAbility(Card card, String manaString, boolean allowGraveyard) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Warp");
        this.zone = Zone.HAND;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = TimingRule.SORCERY;
        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.setAdditionalCostsRuleVisible(false);
        this.allowGraveyard = allowGraveyard;
        this.addWatcher(new WarpAbilityWatcher());
    }

    private WarpAbility(final WarpAbility ability) {
        super(ability);
        this.allowGraveyard = ability.allowGraveyard;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        switch (game.getState().getZone(getSourceId())) {
            case GRAVEYARD:
                if (!allowGraveyard) {
                    break;
                }
            case HAND:
                return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (!super.activate(game, allowedIdentifiers, noMana)) {
            return false;
        }
        this.setCostsTag(WARP_ACTIVATION_VALUE_KEY, null);
        return true;
    }

    @Override
    public WarpAbility copy() {
        return new WarpAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Warp");
        if (getCosts().isEmpty()) {
            sb.append(' ');
        } else {
            sb.append("&mdash;");
        }
        sb.append(getManaCosts().getText());
        if (!getCosts().isEmpty()) {
            sb.append(", ");
            sb.append(getCosts().getText());
            sb.append('.');
        }
        return sb.toString();
    }

    public static String makeWarpString(UUID playerId) {
        return playerId + "- Warped";
    }

    public static boolean checkIfPermanentWarped(Permanent permanent, Game game) {
        return permanent != null
                && game.getPermanentCostsTags()
                .getOrDefault(new MageObjectReference(permanent, game, -1), Collections.emptyMap())
                .containsKey(WarpAbility.WARP_ACTIVATION_VALUE_KEY);
    }
}

class WarpExileEffect extends OneShotEffect {

    private static class WarpCondition implements Condition {

        private final int turnNumber;

        WarpCondition(Game game) {
            this.turnNumber = game.getTurnNum();
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return game.getTurnNum() > turnNumber;
        }
    }

    WarpExileEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        this.setTargetPointer(new FixedTarget(permanent, game));
        staticText = "exile this creature if it was cast for its warp cost";
    }

    private WarpExileEffect(final WarpExileEffect effect) {
        super(effect);
    }

    @Override
    public WarpExileEffect copy() {
        return new WarpExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCardsToExile(
                permanent, source, game, true,
                CardUtil.getExileZoneId(WarpAbility.makeWarpString(player.getId()), game),
                "Warped by " + player.getName()
        );
        CardUtil.makeCardPlayable(
                game, source, permanent.getMainCard(), true,
                Duration.Custom, false, player.getId(), new WarpCondition(game)
        );
        return true;
    }
}

class WarpAbilityWatcher extends Watcher {

    WarpAbilityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (WarpAbility.checkIfPermanentWarped(permanent, game)) {
            game.addDelayedTriggeredAbility(
                    new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new WarpExileEffect(permanent, game)), permanent.getSpellAbility()
            );
        }
    }
}
