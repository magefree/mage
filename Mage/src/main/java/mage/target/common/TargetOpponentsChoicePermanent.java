/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.mage.target.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Mael
 */
public class TargetOpponentsChoicePermanent extends TargetPermanent {

    protected UUID opponentId = null;

    public TargetOpponentsChoicePermanent(FilterPermanent filter) {
        super(1, 1, filter, false);
        this.targetName = filter.getMessage();
    }
    
    public TargetOpponentsChoicePermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
        this.targetName = filter.getMessage();
    }

    public TargetOpponentsChoicePermanent(final TargetOpponentsChoicePermanent target) {
        super(target);
        this.opponentId = target.opponentId;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, UUID sourceId, Game game, boolean flag) {
        if (opponentId != null) {
            return super.canTarget(opponentId, id, sourceId, game, flag);
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (opponentId != null) {
            return super.canTarget(opponentId, id, source, game);
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        return super.chooseTarget(outcome, getOpponentId(playerId, source, game), source, game);
    }

    @Override
    public TargetOpponentsChoicePermanent copy() {
        return new TargetOpponentsChoicePermanent(this);
    }

    private UUID getOpponentId(UUID playerId, Ability source, Game game) {
        if (opponentId == null) {
            TargetOpponent target = new TargetOpponent();
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            }
        }
        return opponentId;
    }

    @Override
    public boolean isRequired(Ability ability) {
        return true; // opponent can't cancel the spell
    }

}
