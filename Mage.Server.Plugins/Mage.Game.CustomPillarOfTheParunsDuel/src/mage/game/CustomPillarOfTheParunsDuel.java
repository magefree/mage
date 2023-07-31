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
 * This is a custom match mode for a non-official format,
 * derived from limited (drafting from cubes or custom sets),
 * that focuses on allowing the players to cast multicolor
 * spells more easily.
 * <p>
 * This is done by having each player conjure a Pillar of the Paruns
 * in play on their first turn, instead of their land drop.
 * It then lets player play multicolor spells of many combinations,
 * in a limited deck, while adding some new layer of strategy
 * during the draft.
 * <p>
 * For balance reason, I did introduce the 6 starting hand size,
 * as the extra Pillar of the Paruns is like a 7th card, and I
 * did not want the player on the draw to discard to handsize.
 * <p> <p>
 * To summarize, this uses the default rules for a 1v1 limited match,
 * with two additional custom rules: <p>
 * -> At the beginning of each player's first main phase, that player
 * conjure into play a Pillar of the Paruns. This does count as a
 * land drop for the turn. <p>
 * -> The starting hand size is 6, not 7.
 * <p> <p>
 * I did took the inspiration for the mode from this cube list (not
 * sure it is the original source for the idea, but i did not found
 * anything else but a youtube video discussing that cube): <p>
 * https://cubecobra.com/cube/overview/allgoldcube
 * <p>
 * And I am working on a remastered set with: <p>
 * https://cubecobra.com/cube/overview/parunsmaster
 *
 * @author Susucr
 */
public class CustomPillarOfTheParunsDuel extends GameImpl {

    public CustomPillarOfTheParunsDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan) {
        super(attackOption, range, mulligan, 20, 40, 6);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);

        getPlayers().forEach((playerId, p) -> {
            addDelayedTriggeredAbility(new AtTheBeginOfPlayerFirstMainPhase(playerId, "Pillar of the Paruns"), null);
        });

        state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
    }

    public CustomPillarOfTheParunsDuel(final CustomPillarOfTheParunsDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new CustomPillarOfTheParunsDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public CustomPillarOfTheParunsDuel copy() {
        return new CustomPillarOfTheParunsDuel(this);
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
