/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.util.CardUtil;
import mage.util.functions.ApplyToPermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class PutTokenOntoBattlefieldCopyTargetEffect extends OneShotEffect {

    private final UUID playerId;
    private final CardType additionalCardType;
    private boolean gainsHaste;
    private final int number;
    private List<Permanent> addedTokenPermanents;
    private String additionalSubType;
    private boolean tapped;
    private boolean attacking;

    public PutTokenOntoBattlefieldCopyTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.playerId = null;
        this.additionalCardType = null;
        this.addedTokenPermanents = new ArrayList<>();
        this.number = 1;
        this.additionalSubType = null;
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId) {
        this(playerId, null, false);
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean gainsHaste) {
        this(playerId, additionalCardType, gainsHaste, 1);
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean gainsHaste, int number) {
        this(playerId, additionalCardType, gainsHaste, number, false, false);
    }

    /**
     *
     * @param playerId null the token is controlled/owned by the controller of
     * the source ability
     * @param additionalCardType the token gains tis card types in addition
     * @param gainsHaste the token gains haste
     * @param number number of tokens to put into play
     */
    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean gainsHaste, int number, boolean tapped, boolean attacking) {
        super(Outcome.PutCreatureInPlay);
        this.playerId = playerId;
        this.additionalCardType = additionalCardType;
        this.gainsHaste = gainsHaste;
        this.addedTokenPermanents = new ArrayList<>();
        this.number = number;
        this.tapped = tapped;
        this.attacking = attacking;
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(final PutTokenOntoBattlefieldCopyTargetEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.additionalCardType = effect.additionalCardType;
        this.gainsHaste = effect.gainsHaste;
        this.addedTokenPermanents = new ArrayList<>(effect.addedTokenPermanents);
        this.number = effect.number;
        this.additionalSubType = effect.additionalSubType;
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Card copyFrom;
        ApplyToPermanent applier = new EmptyApplyToPermanent();
        if (permanent != null) {
            // handle copies of copies
            Permanent copyFromPermanent = permanent;
            for (Effect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof CopyEffect) {
                    CopyEffect copyEffect = (CopyEffect) effect;
                    // there is another copy effect that our targetPermanent copies stats from
                    if (copyEffect.getSourceId().equals(permanent.getId())) {
                        MageObject object = ((CopyEffect) effect).getTarget();
                        if (object instanceof Permanent) {
                            copyFromPermanent = (Permanent) object;
                            if (copyEffect.getApplier() != null) {
                                applier = copyEffect.getApplier();
                            }
                        }
                    }
                }
            }
            copyFrom = copyFromPermanent;
        } else {
            copyFrom = game.getCard(getTargetPointer().getFirst(game, source));
        }

        if (permanent == null && copyFrom == null) {
            return false;
        }

        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(copyFrom); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        applier.apply(game, token);
        if (additionalCardType != null && !token.getCardType().contains(additionalCardType)) {
            token.getCardType().add(additionalCardType);
        }
        if (gainsHaste) {
            token.addAbility(HasteAbility.getInstance());
        }
        if (additionalSubType != null) {
            if (token.getSubtype().contains(additionalSubType)) {
                token.getSubtype().add(additionalSubType);
            }
        }
        token.putOntoBattlefield(number, game, source.getSourceId(), playerId == null ? source.getControllerId() : playerId, tapped, attacking);
        for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                addedTokenPermanents.add(tokenPermanent);
            }
        }
        return true;
    }

    @Override
    public PutTokenOntoBattlefieldCopyTargetEffect copy() {
        return new PutTokenOntoBattlefieldCopyTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Put a token onto the battlefield ");
        if (tapped && !attacking) {
            sb.append("tapped ");
        } else if (!tapped && attacking) {
            sb.append("attacking ");
        } else if (tapped && attacking) {
            sb.append("tapped and attacking ");
        }
        sb.append("that's a copy of target ");
        if (mode.getTargets() != null) {
            sb.append(mode.getTargets().get(0).getTargetName());
        }
        return sb.toString();

    }

    public List<Permanent> getAddedPermanent() {
        return addedTokenPermanents;
    }

    public void setAdditionalSubType(String additionalSubType) {
        this.additionalSubType = additionalSubType;
    }
}
