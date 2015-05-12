/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.constants.SkillLevel;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.match.MatchPlayer;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableView implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID tableId;
    private String gameType;
    private final String deckType;
    private String tableName;
    private String controllerName;
    private final String additionalInfo;
    private Date createTime;
    private TableState tableState;
    private final SkillLevel skillLevel;
    private final String tableStateText;
    private boolean isTournament;
    private List<SeatView> seats = new ArrayList<>();
    private List<UUID> games = new ArrayList<>();

    public TableView(Table table) {    
        this.tableId = table.getId();
        this.gameType = table.getGameType();
        this.tableName = table.getName();
        String tableNameInfo = null;
        if (tableName != null && !tableName.isEmpty()) {
            tableNameInfo = " [" +  table.getName() + "]";
        }
        this.controllerName = table.getControllerName();
        this.tableState = table.getState();
        if (table.getState().equals(TableState.WAITING) || 
                table.getState().equals(TableState.READY_TO_START) ||
                table.getState().equals(TableState.STARTING) ) {
            this.createTime = table.getCreateTime();
        } else {
            if (table.isTournament()) {
                this.createTime = table.getTournament().getStartTime();
            } else {
                this.createTime = table.getMatch().getStartTime();
            }
        }
        this.isTournament = table.isTournament();
        for (Seat seat: table.getSeats()) {
            seats.add(new SeatView(seat));
        }
        if (!table.isTournament()) {
        // MATCH
            if (table.getState().equals(TableState.WAITING) || table.getState().equals(TableState.READY_TO_START)) {
                tableStateText = table.getState().toString() + " (" + table.getMatch().getPlayers().size() + "/"+ table.getSeats().length + ")" +
                        (table.getMatch().getOptions().getPassword().isEmpty() ? "":" PW");
            } else {
                tableStateText = table.getState().toString();
            }
            for (Game game: table.getMatch().getGames()) {
                games.add(game.getId());
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder sbScore = new StringBuilder();
            for(MatchPlayer matchPlayer: table.getMatch().getPlayers()) {
                if (matchPlayer.getPlayer() == null) {
                    sb.append(", ").append("[unknown]");
                    sbScore.append("-").append(matchPlayer.getWins());
                } else if (!matchPlayer.getName().equals(table.getControllerName())) {
                    sb.append(", ").append(matchPlayer.getName());
                    sbScore.append("-").append(matchPlayer.getWins());
                } else {
                    sbScore.insert(0,matchPlayer.getWins()).insert(0," Score: ");
                }
            }
            if (table.getMatch().getDraws() > 0) {
                sbScore.append(" Draws: ").append(table.getMatch().getDraws());
            }
            this.controllerName += sb.toString();
            this.deckType = table.getDeckType() + (tableNameInfo != null ? tableNameInfo : "");
            StringBuilder addInfo = new StringBuilder();
            if (table.getMatch().getGames().isEmpty()) {
                addInfo.append("Wins:").append(table.getMatch().getWinsNeeded());
                addInfo.append(" Time: ").append(table.getMatch().getOptions().getMatchTimeLimit().toString());
                if (table.getMatch().getFreeMulligans() > 0) {
                    addInfo.append(" Free Mul.: ").append(table.getMatch().getFreeMulligans());
                }
            } else {
                addInfo.append("Wins:").append(table.getMatch().getWinsNeeded());
                addInfo.append(sbScore.toString());
            }
            this.additionalInfo = addInfo.toString();
            this.skillLevel = table.getMatch().getOptions().getSkillLevel();
        } else {
        // TOURNAMENT
            if (table.getTournament().getOptions().getNumberRounds() > 0) {
                this.gameType = new StringBuilder(this.gameType).append(" ").append(table.getTournament().getOptions().getNumberRounds()).append(" Rounds").toString();
            }
            StringBuilder sb1 = new StringBuilder();
            for (TournamentPlayer tp: table.getTournament().getPlayers()) {
                if (!tp.getPlayer().getName().equals(table.getControllerName())) {
                    sb1.append(", ").append(tp.getPlayer().getName());
                }
            }
            this.controllerName += sb1.toString();
            StringBuilder infoText = new StringBuilder();
            StringBuilder stateText = new StringBuilder(table.getState().toString());
            infoText.append("Wins:").append(table.getTournament().getOptions().getMatchOptions().getWinsNeeded());
            infoText.append(" Seats: ").append(table.getTournament().getPlayers().size()).append("/").append(table.getNumberOfSeats());
            switch (table.getState()) {
                case WAITING:
                    stateText.append(" (").append(table.getTournament().getPlayers().size()).append("/").append(table.getNumberOfSeats()).append(")");
                    if (!table.getTournament().getOptions().getPassword().isEmpty()) {
                        stateText.append(" PW");
                    }
                case READY_TO_START:
                case STARTING:
                    infoText.append(" Time: ").append(table.getTournament().getOptions().getMatchOptions().getMatchTimeLimit().toString());
                    if (table.getTournament().getOptions().getMatchOptions().getFreeMulligans() > 0) {
                        infoText.append(" Fr.Mul: ").append(table.getTournament().getOptions().getMatchOptions().getFreeMulligans());
                    }
                    if (table.getTournament().getTournamentType().isLimited()) {
                        infoText.append(" Constr.: ").append(table.getTournament().getOptions().getLimitedOptions().getConstructionTime()/60).append(" Min.");
                    }
                    break;
                case DUELING:
                    stateText.append(" Round: ").append(table.getTournament().getRounds().size());
                    break;
                case DRAFTING:
                    Draft draft = table.getTournament().getDraft();
                    if (draft != null) {
                        stateText.append(" ").append(draft.getBoosterNum()).append("/").append(draft.getCardNum() -1);
                    }
                default:
            }
            this.additionalInfo = infoText.toString();
            this.tableStateText = stateText.toString();
            this.deckType =  table.getDeckType() + " " + table.getTournament().getBoosterInfo() + (tableNameInfo != null ? tableNameInfo : "");
            this.skillLevel = table.getTournament().getOptions().getMatchOptions().getSkillLevel();
        }
    }

    public UUID getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public String getGameType() {
        return gameType;
    }

    public String getDeckType() {
        return deckType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TableState getTableState() {
        return tableState;
    }

    public List<SeatView> getSeats() {
        return seats;
    }

    public List<UUID> getGames() {
        return games;
    }

    public boolean isTournament() {
        return this.isTournament;
    }
    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public String getTableStateText() {
        return tableStateText;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

}
