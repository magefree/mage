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

import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentView extends CardView {

    private static final long serialVersionUID = 1L;

    private boolean tapped;
    private final boolean flipped;
    private final boolean phasedIn;
    private final boolean summoningSickness;
    private final int damage;
    private List<UUID> attachments;
    private final CardView original;
    private final boolean copy;
    private final String nameOwner; // only filled if != controller
    private final boolean controlled;
    private final UUID attachedTo;
    private final boolean morphed;
    private final boolean manifested;
    private final boolean attachedToPermanent;

    public PermanentView(Permanent permanent, Card card, UUID createdForPlayerId, Game game) {
        super(permanent, game, permanent.getControllerId().equals(createdForPlayerId));
        this.controlled = permanent.getControllerId().equals(createdForPlayerId);
        this.rules = permanent.getRules(game);
        this.tapped = permanent.isTapped();
        this.flipped = permanent.isFlipped();
        this.phasedIn = permanent.isPhasedIn();
        this.summoningSickness = permanent.hasSummoningSickness();
        this.morphed = permanent.isMorphed();
        this.manifested = permanent.isManifested();
        this.damage = permanent.getDamage();
        if (!permanent.getAttachments().isEmpty()) {
            attachments = new ArrayList<>();
            attachments.addAll(permanent.getAttachments());
        }
        this.attachedTo = permanent.getAttachedTo();
        if (isToken()) {
            original = new CardView(((PermanentToken) permanent).getToken());
            original.expansionSetCode = permanent.getExpansionSetCode();
            tokenSetCode = original.getTokenSetCode();
            tokenDescriptor = original.getTokenDescriptor();
        } else {
            if (card != null) {
                // original may not be face down
                original = new CardView(card);
            } else {
                original = null;
            }
        }
        this.transformed = permanent.isTransformed();
        this.copy = permanent.isCopy();

        // for fipped, transformed or copied cards, switch the names
        if (original != null && !original.getName().equals(this.getName())) {
            if (permanent.isCopy() && permanent.isFlipCard()) {
                this.alternateName = permanent.getFlipCardName();
                this.originalName = this.getName();
            } else {
                if (controlled // controller may always know
                        || (!morphed && !manifested)) { // others don't know for morph or transformed cards
                    this.alternateName = original.getName();
                    this.originalName = this.getName();
                }
            }
        }
        if (!permanent.getOwnerId().equals(permanent.getControllerId())) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                this.nameOwner = owner.getName();
            } else {
                this.nameOwner = "";
            }
        } else {
            this.nameOwner = "";
        }

        if (permanent.isFaceDown(game) && card != null) {
            if (controlled) {
                // must be a morphed or manifested card
                for (Ability permanentAbility : permanent.getAbilities()) {
                    if (permanentAbility instanceof TurnFaceUpAbility && !permanentAbility.getRuleVisible()) {
                        this.rules.add(permanentAbility.getRule(true));
                    }
                    if (permanentAbility.getWorksFaceDown()) {
                        this.rules.add(permanentAbility.getRule());
                    }
                }
                this.name = card.getName();
                this.displayName = card.getName();
                this.expansionSetCode = card.getExpansionSetCode();
                this.cardNumber = card.getCardNumber();
            } else {
                if (permanent.isManifested()) {
                    this.rules.add("A manifested creature card can be turned face up any time for it's mana cost."
                            + " A face-down card can also be turned face up for its morph cost.");
                } else if (permanent.isMorphed()) {
                    this.rules.add("If the controller has priority, he or she may turn this permanent face up."
                            + " This is a special action; it doesn't use the stack. To do this he or she pays the morph costs,"
                            + " then turns this permanent face up.");
                }
            }
        }
        // determines if shown in it's own column
        if (permanent.getAttachedTo() != null) {
            attachedToPermanent = game.getPermanent(permanent.getAttachedTo()) != null;
        } else {
            attachedToPermanent = false;
        }
    }

    public boolean isTapped() {
        return tapped;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public boolean isCopy() {
        return copy;
    }

    public boolean isPhasedIn() {
        return phasedIn;
    }

    public boolean hasSummoningSickness() {
        return summoningSickness;
    }

    public List<UUID> getAttachments() {
        return attachments;
    }

    public CardView getOriginal() {
        return original;
    }

    public void overrideTapped(boolean tapped) {
        this.tapped = tapped;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public boolean isControlled() {
        return controlled;
    }

    public UUID getAttachedTo() {
        return attachedTo;
    }

    public boolean isAttachedTo() {
        return attachedTo != null;
    }

    public boolean isAttachedToPermanent() {
        return attachedToPermanent;
    }

    public boolean isMorphed() {
        return morphed;
    }

    public boolean isManifested() {
        return manifested;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final PermanentView that = (PermanentView) o;

        if (isTapped() != that.isTapped()) return false;
        if (isFlipped() != that.isFlipped()) return false;
        if (isPhasedIn() != that.isPhasedIn()) return false;
        if (summoningSickness != that.summoningSickness) return false;
        if (getDamage() != that.getDamage()) return false;
        if (isCopy() != that.isCopy()) return false;
        if (isControlled() != that.isControlled()) return false;
        if (isMorphed() != that.isMorphed()) return false;
        if (isManifested() != that.isManifested()) return false;
        if (isAttachedToPermanent() != that.isAttachedToPermanent()) return false;
        if (getAttachments() != null ? !getAttachments().equals(that.getAttachments()) : that
            .getAttachments() != null)
            return false;
        if (getOriginal() != null ? !getOriginal().equals(that.getOriginal()) : that.getOriginal
            () != null)
            return false;
        if (getNameOwner() != null ? !getNameOwner().equals(that.getNameOwner()) : that
            .getNameOwner() != null)
            return false;
        return isAttachedTo() == that.isAttachedTo();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isTapped() ? 1 : 0);
        result = 31 * result + (isFlipped() ? 1 : 0);
        result = 31 * result + (isPhasedIn() ? 1 : 0);
        result = 31 * result + (summoningSickness ? 1 : 0);
        result = 31 * result + getDamage();
        result = 31 * result + (getAttachments() != null ? getAttachments().hashCode() : 0);
        result = 31 * result + (getOriginal() != null ? getOriginal().hashCode() : 0);
        result = 31 * result + (isCopy() ? 1 : 0);
        result = 31 * result + (getNameOwner() != null ? getNameOwner().hashCode() : 0);
        result = 31 * result + (isControlled() ? 1 : 0);
        result = 31 * result + (isAttachedTo() ? 1 : 0);
        result = 31 * result + (isMorphed() ? 1 : 0);
        result = 31 * result + (isManifested() ? 1 : 0);
        result = 31 * result + (isAttachedToPermanent() ? 1 : 0);
        return result;
    }
}
