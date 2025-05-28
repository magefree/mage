
package mage.cards.d;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.StaticHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.filter.FilterObject;
import mage.players.Player;
import mage.target.TargetSource;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class DesperateGambit extends CardImpl {

    public DesperateGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Choose a source you control and flip a coin. If you win the flip, the next time that source would deal damage this turn, it deals double that damage instead. If you lose the flip, the next time it would deal damage this turn, prevent that damage.
        this.getSpellAbility().addEffect(new DesperateGambitEffect());
    }

    private DesperateGambit(final DesperateGambit card) {
        super(card);
    }

    @Override
    public DesperateGambit copy() {
        return new DesperateGambit(this);
    }
}

class DesperateGambitEffect extends PreventionEffectImpl {
    
    private final TargetSource target;
    private boolean wonFlip;

    public DesperateGambitEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Choose a source you control and flip a coin. If you win the flip, the next time that source would deal damage this turn, it deals double that damage instead. If you lose the flip, the next time it would deal damage this turn, prevent that damage";
        this.target = new TargetControlledSource();
    }

    private DesperateGambitEffect(final DesperateGambitEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            discard();
            return;
        }

        this.target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), source, game);
        this.wonFlip = you.flipCoin(source, game, true);
        game.informPlayers("The next time " + game.getObject(target.getFirstTarget()).getLogName()
                + " would deal combat damage this turn, "
                + (wonFlip ? "it deals double that damage instead." : "prevent that damage.)"));
    }

    @Override
    public DesperateGambitEffect copy() {
        return new DesperateGambitEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(target.getFirstTarget())) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object != null) {
            if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
                if (wonFlip) {
                    event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
                    this.discard();
                } else {
                    preventDamageAction(event, source, game);
                    this.discard();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasHint() {
        return true;
    }

    static StaticHint wonHint = new StaticHint(
            "The next time {this} would deal combat damage this turn, it deals double that damage instead.");
    static StaticHint lostHint = new StaticHint(
            "The next time {this} would deal combat damage this turn, prevent that damage.");

    @Override
    public List<Hint> getAffectedHints(Permanent permanent, Ability source, Game game) {
        if (!permanent.getId().equals(target.getFirstTarget())) {
            return Collections.emptyList();
        }

        return Arrays.asList(wonFlip ? wonHint : lostHint);
    }
}

class TargetControlledSource extends TargetSource {

    public TargetControlledSource() {
        super(1, 1, new FilterObject("source you control"));
    }

    private TargetControlledSource(final TargetControlledSource target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) 
                && Objects.equals(stackObject.getControllerId(), sourceControllerId)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (Objects.equals(permanent.getControllerId(), sourceControllerId)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (Objects.equals(player, game.getPlayer(sourceControllerId))) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
                // 108.4a If anything asks for the controller of a card that doesn't have one (because it's not a permanent or spell), use its owner instead.
                for (Card card : game.getExile().getAllCards(game)) {
                    if (Objects.equals(card.getOwnerId(), sourceControllerId)) {
                        count++;
                        if (count >= this.minNumberOfTargets) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) 
                && Objects.equals(stackObject.getControllerId(), sourceControllerId)) {
                possibleTargets.add(stackObject.getId());
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (Objects.equals(permanent.getControllerId(), sourceControllerId)) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (Objects.equals(player, game.getPlayer(sourceControllerId))) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    possibleTargets.add(card.getId());
                }
                // 108.4a If anything asks for the controller of a card that doesn't have one (because it's not a permanent or spell), use its owner instead.
                for (Card card : game.getExile().getAllCards(game)) {
                    if (Objects.equals(card.getOwnerId(), sourceControllerId)) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetControlledSource copy() {
        return new TargetControlledSource(this);
    }
}
