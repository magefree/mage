package mage.cards.e;

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

import java.util.UUID;

/**
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

        // Players gain hexproof until end of turn. Players can't lose life this turn and players can't lose the game or win the game this turn.
        this.getSpellAbility().addEffect(new EverybodyLivesPlayerEffect());
        this.getSpellAbility().addEffect(new EverybodyLivesCantLoseOrWinGameEffect());
    }

    private EverybodyLives(final EverybodyLives card) {
        super(card);
    }

    @Override
    public EverybodyLives copy() {
        return new EverybodyLives(this);
    }
}

class EverybodyLivesPlayerEffect extends ContinuousEffectImpl {

    EverybodyLivesPlayerEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Players gain hexproof until end of turn. Players can't lose life this turn";
    }

    private EverybodyLivesPlayerEffect(final EverybodyLivesPlayerEffect effect) {
        super(effect);
    }

    @Override
    public EverybodyLivesPlayerEffect copy() {
        return new EverybodyLivesPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.addAbility(HexproofAbility.getInstance());
                player.setCanLoseLife(false);
            }
        }
        return true;
    }
}

class EverybodyLivesCantLoseOrWinGameEffect extends ContinuousRuleModifyingEffectImpl {

    EverybodyLivesCantLoseOrWinGameEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "and players can't lose the game or win the game this turn";
    }

    private EverybodyLivesCantLoseOrWinGameEffect(final EverybodyLivesCantLoseOrWinGameEffect effect) {
        super(effect);
    }

    @Override
    public EverybodyLivesCantLoseOrWinGameEffect copy() {
        return new EverybodyLivesCantLoseOrWinGameEffect(this);
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
