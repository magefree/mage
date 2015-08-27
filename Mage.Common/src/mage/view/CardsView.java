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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Zone;
import static mage.constants.Zone.ALL;
import static mage.constants.Zone.BATTLEFIELD;
import static mage.constants.Zone.COMMAND;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.STACK;
import mage.game.Game;
import mage.game.GameState;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardsView extends LinkedHashMap<UUID, CardView> {

    public CardsView() {
    }

    public CardsView(Collection<? extends Card> cards) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card));
        }
    }

    public CardsView(Game game, Collection<? extends Card> cards) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card, game, false));
        }
    }

    public CardsView(Game game, Collection<? extends Card> cards, boolean showFaceDown) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card, game, false, showFaceDown));
        }
    }

    public CardsView(Collection<? extends Ability> abilities, Game game) {
        for (Ability ability : abilities) {
            MageObject sourceObject = null;
            AbilityView abilityView = null;
            boolean isCard = false;
            boolean isPermanent = false;
            switch (ability.getZone()) {
                case ALL:
                case EXILED:
                case GRAVEYARD:
                    sourceObject = game.getCard(ability.getSourceId());
                    if (sourceObject == null) {
                        sourceObject = game.getPermanent(ability.getSourceId());
                    }
                    isCard = true;
                    break;
                case BATTLEFIELD:
                    sourceObject = game.getPermanent(ability.getSourceId());
                    if (sourceObject == null) {
                        sourceObject = (Permanent) game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD);
                    }
                    isPermanent = true;
                    break;
                case STACK:
                    sourceObject = game.getObject(ability.getSourceId());
                    if (sourceObject instanceof Card) {
                        isCard = true;
                    }
                    break;
                case COMMAND:
                    sourceObject = game.getObject(ability.getSourceId());
                    if (sourceObject instanceof Emblem) {
                        Card planeswalkerCard = game.getCard(((Emblem) sourceObject).getSourceId());
                        if (planeswalkerCard != null) {
                            if (!planeswalkerCard.getCardType().contains(CardType.PLANESWALKER)) {
                                if (planeswalkerCard.getSecondCardFace() != null) {
                                    planeswalkerCard = planeswalkerCard.getSecondCardFace();
                                }
                            }
                            abilityView = new AbilityView(ability, "Emblem " + planeswalkerCard.getName(), new CardView(new EmblemView((Emblem) sourceObject, planeswalkerCard)));
                            abilityView.setName("Emblem " + planeswalkerCard.getName());
                            abilityView.setExpansionSetCode(planeswalkerCard.getExpansionSetCode());
                        } else {
                            throw new IllegalArgumentException("Source card for emblem not found.");
                        }
                    }
                    break;
            }
            if (sourceObject != null) {
                if (abilityView == null) {
                    CardView sourceCardView;
                    if (isPermanent) {
                        sourceCardView = new CardView((Permanent) sourceObject);
                    } else if (isCard) {
                        sourceCardView = new CardView((Card) sourceObject);
                    } else {
                        sourceCardView = new CardView(sourceObject);
                    }
                    abilityView = new AbilityView(ability, sourceObject.getName(), sourceCardView);
                }
                if (ability.getTargets().size() > 0) {
                    abilityView.setTargets(ability.getTargets());
                } else {
                    List<UUID> abilityTargets = new ArrayList<>();
                    for (Effect effect : ability.getEffects()) {
                        TargetPointer targetPointer = effect.getTargetPointer();
                        if (targetPointer != null) {
                            List<UUID> targetList = targetPointer.getTargets(game, ability);
                            abilityTargets.addAll(targetList);
                        }
                    }
                    if (!abilityTargets.isEmpty()) {
                        abilityView.overrideTargets(abilityTargets);
                        List<String> names = new ArrayList<>();
                        for (UUID uuid : abilityTargets) {
                            MageObject mageObject = game.getObject(uuid);
                            if (mageObject != null) {
                                if ((mageObject instanceof Card) && ((Card) mageObject).isFaceDown(game)) {
                                    continue;
                                }
                                names.add(GameLog.getColoredObjectIdNameForTooltip(mageObject));
                            }
                        }
                        if (!names.isEmpty()) {
                            abilityView.getRules().add("<i>Related objects: " + names.toString() + "</i>");
                        }
                    }
                }
                this.put(ability.getId(), abilityView);
            }
        }
    }

    public CardsView(Collection<? extends Ability> abilities, GameState state) {
        for (Ability ability : abilities) {
            Card sourceCard = state.getPermanent(ability.getSourceId());
            if (sourceCard != null) {
                this.put(ability.getId(), new AbilityView(ability, sourceCard.getName(), new CardView(sourceCard)));
            }
        }
    }

}
