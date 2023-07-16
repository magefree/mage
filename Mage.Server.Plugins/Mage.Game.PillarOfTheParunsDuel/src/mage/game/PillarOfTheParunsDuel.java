package mage.game;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.constants.*;
import mage.game.events.GameEvent;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public class PillarOfTheParunsDuel extends GameImpl {

    public PillarOfTheParunsDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan) {
        super(attackOption, range, mulligan, 20, 40, 6);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);

        getPlayers().forEach((playerId,p) -> {
            addDelayedTriggeredAbility(new AtTheBeginOfPlayerFirstMainPhase(playerId, "Pillar of the Paruns"), null);
        });

        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    public PillarOfTheParunsDuel(final PillarOfTheParunsDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new PillarOfTheParunsDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public PillarOfTheParunsDuel copy() {
        return new PillarOfTheParunsDuel(this);
    }

}

class InitPillarOfTheParunsEffect extends OneShotEffect {

    private UUID playerId;
    private String cardName;

    InitPillarOfTheParunsEffect(UUID playerId, String cardName){
        super(Outcome.PutLandInPlay);
        this.playerId = playerId;
        this.cardName = cardName;
        this.staticText = "conjure " + cardName + " in play. It does count as a land played for the turn.";
    }

    private InitPillarOfTheParunsEffect(final InitPillarOfTheParunsEffect effect){
        super(effect);
        this.playerId = effect.playerId;
        this.cardName = effect.cardName;
    }

    @Override
    public InitPillarOfTheParunsEffect copy(){
        return new InitPillarOfTheParunsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Player player = game.getPlayer(playerId);
        if(player == null){
            return false;
        }

        new ConjureCardEffect(cardName, Zone.BATTLEFIELD, 1).apply(game, source);
        player.incrementLandsPlayed();

        return true;
    }
}

class AtTheBeginOfPlayerFirstMainPhase extends DelayedTriggeredAbility {

    AtTheBeginOfPlayerFirstMainPhase(UUID playerId, String cardName) {
        super(new InitPillarOfTheParunsEffect(playerId, cardName), Duration.Custom, true);
        setControllerId(playerId);
        setTriggerPhrase("At the beginning of your first main phase, ");
    }

    private AtTheBeginOfPlayerFirstMainPhase(final AtTheBeginOfPlayerFirstMainPhase ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfPlayerFirstMainPhase copy() {
        return new AtTheBeginOfPlayerFirstMainPhase(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(getControllerId());
    }
}
