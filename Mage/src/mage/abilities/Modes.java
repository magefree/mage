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
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends LinkedHashMap<UUID, Mode> {

    private UUID modeId;
    private Set<UUID> selectedModes = new LinkedHashSet<UUID>();
    private int minModes;
    private int maxModes;

    public Modes() {
        Mode mode = new Mode();
        this.put(mode.getId(), mode);
        this.modeId = mode.getId();
        this.minModes = 1;
        this.maxModes = 1;
        this.selectedModes.add(modeId);

    }

    public Modes(Modes modes) {
        this.modeId = modes.modeId;
        for (Map.Entry<UUID, Mode> entry: modes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        this.minModes = modes.minModes;
        this.maxModes = modes.maxModes;
        this.selectedModes.addAll(modes.selectedModes);
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

    public void setMode(Mode mode) {
        if (this.containsKey(mode.getId())) {
            this.modeId = mode.getId();
            this.selectedModes.add(mode.getId());
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
            // player chooses modes manually
            Player player = game.getPlayer(source.getControllerId());
            while (this.selectedModes.size() < this.getMaxModes()) {
                Mode choice = player.chooseMode(this, source, game);
                if (choice == null) {
                    return this.selectedModes.size() >= this.getMinModes();
                }
                setMode(choice);
                this.selectedModes.add(choice.getId());
            }
            return true;
        }
        this.modeId = this.values().iterator().next().getId();
        this.selectedModes.add(modeId);
        return true;
    }

    public String getText() {
        String andOr = "";
        StringBuilder sb = new StringBuilder();
        if (this.size() > 1) {
            if (this.getMinModes() == 1 && this.getMaxModes() == 3) {
                sb.append("Choose one or more - ");
                andOr = "; and/or ";
            }else if (this.getMinModes() == 1 && this.getMaxModes() == 2) {
                sb.append("Choose one or both - ");
                andOr = "; and/or ";
            } else if (this.getMinModes() == 2 && this.getMaxModes() == 2) {
                sb.append("Choose two - ");
                andOr = "; or ";
            } else {
                sb.append("Choose one - ");
                andOr = "; or ";
            }
        }
        for (Mode mode: this.values()) {
            sb.append(mode.getEffects().getText(mode));
            if (this.size() > 1) {
                if (sb.length() > 2 && sb.substring(sb.length()-2, sb.length()).equals(". ")) {
                    sb.delete(sb.length()-2, sb.length());
                }
                sb.append(andOr);
            }
        }
        if (this.size() > 1) {
            sb.delete(sb.length() - andOr.length(), sb.length());
            sb.append(".");
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
