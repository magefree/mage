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
package mage.abilities.effects.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileTargetEffect extends OneShotEffect {

    private Zone onlyFromZone;
    private String exileZone = null;
    private UUID exileId = null;
    protected boolean multitargetHandling;

    public ExileTargetEffect(String effectText) {
        this();
        this.staticText = effectText;
    }

    public ExileTargetEffect() {
        this(null, "");
    }

    public ExileTargetEffect(UUID exileId, String exileZone) {
        this(exileId, exileZone, null);
    }

    public ExileTargetEffect(UUID exileId, String exileZone, Zone onlyFromZone) {
        this(exileId, exileZone, onlyFromZone, false);
    }

    public ExileTargetEffect(UUID exileId, String exileZone, Zone onlyFromZone, boolean multitargetHandling) {
        super(Outcome.Exile);
        this.exileZone = exileZone;
        this.exileId = exileId;
        this.onlyFromZone = onlyFromZone;
        this.multitargetHandling = multitargetHandling;
    }

    public ExileTargetEffect(final ExileTargetEffect effect) {
        super(effect);
        this.exileZone = effect.exileZone;
        this.exileId = effect.exileId;
        this.onlyFromZone = effect.onlyFromZone;
        this.multitargetHandling = effect.multitargetHandling;
    }

    @Override
    public ExileTargetEffect copy() {
        return new ExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> toExile = new LinkedHashSet<>();
            if (multitargetHandling && source.getTargets().size() > 1 && targetPointer instanceof FirstTargetPointer) { // Decimate
                for (Target target : source.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(targetId);
                        if (permanent != null) {
                            Zone currentZone = game.getState().getZone(permanent.getId());
                            if (currentZone != Zone.EXILED && (onlyFromZone == null || onlyFromZone == Zone.BATTLEFIELD)) {
                                toExile.add(permanent);
                            }
                        } else {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                Zone currentZone = game.getState().getZone(card.getId());
                                if (currentZone != Zone.EXILED && (onlyFromZone == null || onlyFromZone == currentZone)) {
                                    toExile.add(card);
                                }
                            }
                        }
                    }
                }
            } else {
                for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        Zone currentZone = game.getState().getZone(permanent.getId());
                        if (currentZone != Zone.EXILED && (onlyFromZone == null || onlyFromZone == Zone.BATTLEFIELD)) {
                            toExile.add(permanent);
                        }
                    } else {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            Zone currentZone = game.getState().getZone(card.getId());
                            if (currentZone != Zone.EXILED && (onlyFromZone == null || onlyFromZone == currentZone)) {
                                toExile.add(card);
                            }
                        } else {
                            StackObject stackObject = game.getStack().getStackObject(targetId);
                            if (stackObject instanceof Spell) {
                                toExile.add((Spell) stackObject);
                            }
                        }
                    }
                }
            }
            controller.moveCardsToExile(toExile, source, game, true, exileId, exileZone);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("exile that permanent"); // this will be used if the target is set by target pointer and staticText not set.
        } else {
            Target target;
            if (targetPointer instanceof SecondTargetPointer && mode.getTargets().size() > 1) {
                target = mode.getTargets().get(1);
            } else {
                target = mode.getTargets().get(0);
            }
            if (target.getNumberOfTargets() == 1) {
                String targetName = target.getTargetName();
                sb.append("exile ");
                if (!targetName.startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(targetName);
            } else if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
                sb.append("exile up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName());
            } else {
                sb.append("exile ").append(CardUtil.numberToText(target.getNumberOfTargets())).append(" target ").append(target.getTargetName());
            }
        }
        return sb.toString();
    }
}
