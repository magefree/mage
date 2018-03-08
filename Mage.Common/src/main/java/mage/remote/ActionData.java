/*
 * Copyright 2018 nanarpuss_at_googlemail.com. All rights reserved.
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
package mage.remote;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.util.UUID;

public class ActionData {

    @Expose
    public UUID gameId;
    @Expose
    public String sessionId;
    @Expose
    public String type;
    @Expose
    public Object value;
    @Expose
    public String message;

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setExclusionStrategies(new CustomExclusionStrategy()).create();

        return gson.toJson(this);
    }

    public ActionData(String type, UUID gameId, String sessionId) {
        this.type = type;
        this.sessionId = sessionId;
        this.gameId = gameId;
    }

    public ActionData(String type, UUID gameId) {
        this.type = type;
        this.gameId = gameId;
    }

    public class CustomExclusionStrategy implements ExclusionStrategy {

        // FIXME: Very crude way of whitelisting, as it applies to all levels of the JSON tree.
        private final java.util.Set<String> KEEP = new java.util.HashSet<>(
                java.util.Arrays.asList(
                        new String[]{
                            "id",
                            "choice",
                            "damage",
                            "abilityType",
                            "ability",
                            "abilities",
                            "method",
                            "data",
                            "options",
                            "life",
                            "players",
                            "zone",
                            "step",
                            "phase",
                            "attackers",
                            "blockers",
                            "tapped",
                            "damage",
                            "combat",
                            "paid",
                            "hand",
                            "stack",
                            "convertedManaCost",
                            "gameId",
                            "canPlayInHand",
                            "gameView",
                            "sessionId",
                            "power",
                            "choices",
                            "targets",
                            "loyalty",
                            "toughness",
                            "power",
                            "type",
                            "priorityTime",
                            "manaCost",
                            "value",
                            "message",
                            "cardsView",
                            "name",
                            "count",
                            "counters",
                            "battlefield",
                            "parentId"
                        }));

        public CustomExclusionStrategy() {
        }

        // This method is called for all fields. if the method returns true the
        // field is excluded from serialization
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name = f.getName();
            return !KEEP.contains(name);
        }

        // This method is called for all classes. If the method returns true the
        // class is excluded.
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
