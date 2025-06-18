package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class CourageousResolve extends CardImpl {

    public CourageousResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Up to one target creature you control gains protection from each of your opponents until end of turn.
        // Draw a card. (It can't be blocked, targeted, dealt damage, enchanted, or equipped by anything controlled by those players.)
        this.getSpellAbility().addEffect(new CourageousResolveEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("Draw a card. <i>(It can't be blocked, targeted, dealt damage, enchanted, or equipped by anything controlled by those players.)</i>"));


        // Fateful hour — If you have 5 or less life, you can't lose life this turn, you can't lose the game this turn,
        // and your opponents can't win the game this turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new CourageousResolveCantLoseLifeEffect(), FatefulHourCondition.instance, "<br><i>Fateful hour</i> &mdash; If you have 5 or less life, you can't lose life this turn"));
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(new CourageousResolveWinLoseEffect(), FatefulHourCondition.instance));


    }

    private CourageousResolve(final CourageousResolve card) {
        super(card);
    }

    @Override
    public CourageousResolve copy() {
        return new CourageousResolve(this);
    }
}
//Based on Cliffside Rescuer

class CourageousResolveEffect extends OneShotEffect {

    CourageousResolveEffect() {
        super(Outcome.Benefit);
        staticText = "Up to one target creature you control gains protection from each of your opponents until end of turn.";
    }

    private CourageousResolveEffect(final CourageousResolveEffect effect) {
        super(effect);
    }

    @Override
    public CourageousResolveEffect copy() {
        return new CourageousResolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainAbilityTargetEffect(new CourageousResolveProtectionAbility(
                game.getOpponents(source.getControllerId())
        ), Duration.EndOfTurn), source);
        return true;
    }
}

class CourageousResolveProtectionAbility extends ProtectionAbility {

    private final Set<UUID> playerSet = new HashSet<>();

    CourageousResolveProtectionAbility(Set<UUID> playerSet) {
        super(StaticFilters.FILTER_CARD);
        this.playerSet.addAll(playerSet);
    }

    private CourageousResolveProtectionAbility(final CourageousResolveProtectionAbility ability) {
        super(ability);
        this.playerSet.addAll(ability.playerSet);
    }

    @Override
    public CourageousResolveProtectionAbility copy() {
        return new CourageousResolveProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} has protection from each opponent";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        if (source instanceof Permanent) {
            return playerSet.stream().noneMatch(((Permanent) source)::isControlledBy);
        }
        if (source instanceof Spell) {
            return playerSet.stream().noneMatch(((Spell) source)::isControlledBy);
        }
        if (source instanceof StackObject) {
            return playerSet.stream().noneMatch(((StackObject) source)::isControlledBy);
        }
        if (source instanceof Card) {
            return playerSet.stream().noneMatch(((Card) source)::isOwnedBy);
        }
        return true;
    }
}

class CourageousResolveCantLoseLifeEffect extends ContinuousEffectImpl {

    CourageousResolveCantLoseLifeEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    protected CourageousResolveCantLoseLifeEffect(final CourageousResolveCantLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public CourageousResolveCantLoseLifeEffect copy() {
        return new CourageousResolveCantLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.setCanLoseLife(false);
            return true;
        }
        return false;
    }
}

//Based on Angels Grace
class CourageousResolveWinLoseEffect extends ContinuousRuleModifyingEffectImpl {

    CourageousResolveWinLoseEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, true);
        staticText = ", you can't lose the game this turn, and your opponents can't win the game this turn";
    }

    private CourageousResolveWinLoseEffect(final CourageousResolveWinLoseEffect effect) {
        super(effect);
    }

    @Override
    public CourageousResolveWinLoseEffect copy() {
        return new CourageousResolveWinLoseEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.WINS || event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return (event.getType() == GameEvent.EventType.WINS
                && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.LOSES
                && event.getPlayerId().equals(source.getControllerId()));
    }
}
