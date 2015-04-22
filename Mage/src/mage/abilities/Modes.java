/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends LinkedHashMap<UUID, Mode> {

    private UUID modeId;
    private final Set<UUID> selectedModes = new LinkedHashSet<>();
    private int minModes;
    private int maxModes;
    private TargetController modeChooser;

    public Modes() {
        Mode mode = new Mode();
        this.put(mode.getId(), mode);
        this.modeId = mode.getId();
        this.minModes = 1;
        this.maxModes = 1;
        this.selectedModes.add(modeId);
        this.modeChooser = TargetController.YOU;
    }

    public Modes(Modes modes) {
        this.modeId = modes.modeId;
        for (Map.Entry<UUID, Mode> entry: modes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        this.minModes = modes.minModes;
        this.maxModes = modes.maxModes;
        this.selectedModes.addAll(modes.selectedModes);
        this.modeChooser = modes.modeChooser;
    }

    public Modes copy() {
        return new Modes(this);
    }

    public Mode getMode() {
        return get(modeId);
    }

    public Set<UUID> getSelectedModes() {
        return selectedModes;
    }

    public void setMinModes(int minModes) {
        this.minModes = minModes;
    }

    public int getMinModes() {
        return this.minModes;
    }

    public void setMaxModes(int maxModes) {
        this.maxModes = maxModes;
    }

    public int getMaxModes() {
        return this.maxModes;
    }
    
    public void setModeChooser(TargetController modeChooser) {
        this.modeChooser = modeChooser;
    }
    
    public TargetController getModeChooser() {
        return this.modeChooser;
    }

    public void setActiveMode(UUID modeId) {
        if (selectedModes.contains(modeId)) {
            this.modeId = modeId;
        }
    }

    public void setMode(Mode mode) {
        if (this.containsKey(mode.getId())) {
            this.modeId = mode.getId();
            this.selectedModes.add(mode.getId());
            Set<UUID> copySelectedModes = new LinkedHashSet<>();
            copySelectedModes.addAll(selectedModes);
            selectedModes.clear();
            for (UUID basicModeId: this.keySet()) {
                if (copySelectedModes.contains(basicModeId)) {
                    selectedModes.add(basicModeId);
                }
            }
        }
    }

    public void addMode(Mode mode) {
        this.put(mode.getId(), mode);
    }

    public boolean choose(Game game, Ability source) {
        if (this.size() > 1) {
            this.selectedModes.clear();
            // check if mode modifying abilities exist
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                for (Ability modeModifyingAbility : card.getAbilities()) {
                    if (modeModifyingAbility instanceof OptionalAdditionalModeSourceCosts) {
                        ((OptionalAdditionalModeSourceCosts)modeModifyingAbility).addOptionalAdditionalModeCosts(source, game);
                    }
                }
            }
            // check if all modes can be activated automatically
            if (this.size() == this.getMinModes()) {
                for (Mode mode: this.values()) {
                    if (mode.getTargets().canChoose(source.getSourceId(), source.getControllerId(), game)) {
                        this.selectedModes.add(mode.getId());
                    }
                }
                return selectedModes.size() > 0;
            }
            
            // 700.2d
            // Some spells and abilities specify that a player other than their controller chooses a mode for it.
            // In that case, the other player does so when the spell or ability’s controller normally would do so.
            // If there is more than one other player who could make such a choice, the spell or ability’s controller decides which of those players will make the choice. 
            UUID playerId = null;
            if (modeChooser == TargetController.OPPONENT) {
                TargetOpponent targetOpponent = new TargetOpponent();
                if (targetOpponent.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game)) {
                    playerId = targetOpponent.getFirstTarget();
                }
            } else {
                playerId = source.getControllerId();
            }
            if (playerId == null) {
                return false;
            }
            Player player = game.getPlayer(playerId);
            
            // player chooses modes manually
            while (this.selectedModes.size() < this.getMaxModes()) {
                Mode choice = player.chooseMode(this, source, game);
                if (choice == null) {
                    return this.selectedModes.size() >= this.getMinModes();
                }
                setMode(choice);
            }
            return true;
        }
        this.modeId = this.values().iterator().next().getId();
        this.selectedModes.add(modeId);
        return true;
    }

    public String getText() {
        if (this.size() <= 1) {
            return this.getMode().getEffects().getText(this.getMode());
        } 
        StringBuilder sb = new StringBuilder();
        if (this.getMinModes() == 1 && this.getMaxModes() == 3) {
            sb.append("choose one or more ");
        }else if (this.getMinModes() == 1 && this.getMaxModes() == 2) {
            sb.append("choose one or both ");
        } else if (this.getMinModes() == 2 && this.getMaxModes() == 2) {
            sb.append("choose two ");
        } else {
            sb.append("choose one ");
        }
        sb.append("&mdash;<br>");            
        for (Mode mode: this.values()) {
            sb.append("&bull  ");
            sb.append(mode.getEffects().getTextStartingUpperCase(mode));
            sb.append("<br>");
        }        
        return sb.toString();
    }

    public String getText(String sourceName) {
        String text = getText();
        text = text.replace("{this}", sourceName);
        text = text.replace("{source}", sourceName);
        return text;
    }

}
