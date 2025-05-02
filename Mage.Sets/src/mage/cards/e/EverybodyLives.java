package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author padfoot
 */
public final class EverybodyLives extends CardImpl {

    public EverybodyLives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // All creatures gain hexproof and indestructible until end of turn. 
	this.getSpellAbility().addEffect(new GainAbilityAllEffect(
		HexproofAbility.getInstance(), 
		Duration.EndOfTurn, 
		StaticFilters.FILTER_PERMANENT_ALL_CREATURES
	).setText("all creatures gain hexproof"));
	this.getSpellAbility().addEffect(new GainAbilityAllEffect(
		IndestructibleAbility.getInstance(), 
		Duration.EndOfTurn, 
		StaticFilters.FILTER_PERMANENT_ALL_CREATURES
	).setText("and indestructible until end of turn"));
	// Players gain hexproof until end of turn.
	this.getSpellAbility().addEffect(new GainAbilityAllPlayersEffect(HexproofAbility.getInstance()));
	// Players can't lose life this turn and players can't lose the game or win the this turn.
        this.getSpellAbility().addEffect(new CantLoseLifeAllEffect());
        this.getSpellAbility().addEffect(new CantLoseOrWinGameAllEffect().concatBy("and"));
    }

    private EverybodyLives(final EverybodyLives card) {
        super(card);
    }

    @Override
    public EverybodyLives copy() {
        return new EverybodyLives(this);
    }
}

class GainAbilityAllPlayersEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public GainAbilityAllPlayersEffect(Ability ability) {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability.copy();
        this.staticText = "Players gain " + ability.getRule() + " until end of turn";
    }

    protected GainAbilityAllPlayersEffect(final GainAbilityAllPlayersEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public GainAbilityAllPlayersEffect copy() {
        return new GainAbilityAllPlayersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.addAbility(ability);
            }
        }
        return true;
    }
}

class CantLoseLifeAllEffect extends ContinuousEffectImpl {

    public CantLoseLifeAllEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "players can't lose life this turn";
    }

    protected CantLoseLifeAllEffect(final CantLoseLifeAllEffect effect) {
        super(effect);
    }

    @Override
    public CantLoseLifeAllEffect copy() {
        return new CantLoseLifeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.setCanLoseLife(false);
            }
        }           
        return true;
    }
}

class CantLoseOrWinGameAllEffect extends ContinuousRuleModifyingEffectImpl {

    public CantLoseOrWinGameAllEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "players can't lose the game or win the game this turn";
    }

    protected CantLoseOrWinGameAllEffect(final CantLoseOrWinGameAllEffect effect) {
        super(effect);
    }

    @Override
    public CantLoseOrWinGameAllEffect copy() {
        return new CantLoseOrWinGameAllEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.LOSES || event.getType() == GameEvent.EventType.WINS);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

