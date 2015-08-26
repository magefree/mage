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
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.targetpointer.FixedTarget;
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
    private List<Permanent> addedTokenPermanents;

    public PutTokenOntoBattlefieldCopyTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.playerId = null;
        this.additionalCardType = null;
        this.addedTokenPermanents = new ArrayList<>();
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId) {
        this(playerId, null, false);
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean gainsHaste) {
        super(Outcome.PutCreatureInPlay);
        this.playerId = playerId;
        this.additionalCardType = additionalCardType;
        this.gainsHaste = gainsHaste;
        this.addedTokenPermanents = new ArrayList<>();
    }

    public PutTokenOntoBattlefieldCopyTargetEffect(final PutTokenOntoBattlefieldCopyTargetEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.additionalCardType = effect.additionalCardType;
        this.gainsHaste = effect.gainsHaste;
        this.addedTokenPermanents.addAll(effect.addedTokenPermanents);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            // handle copies of copies
            Permanent copyFromPermanent = permanent;
            ApplyToPermanent applier = new EmptyApplyToPermanent();
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

            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(copyFromPermanent); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
            if (additionalCardType != null && !token.getCardType().contains(additionalCardType)) {
                token.getCardType().add(additionalCardType);
            }
            if (gainsHaste) {
                token.addAbility(HasteAbility.getInstance());
            }
            token.putOntoBattlefield(1, game, source.getSourceId(), playerId == null ? source.getControllerId() : playerId);
            for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    addedTokenPermanents.add(tokenPermanent);
                    game.copyPermanent(copyFromPermanent, tokenPermanent, source, applier);
                    if (additionalCardType != null) {
                        ContinuousEffect effect = new AddCardTypeTargetEffect(additionalCardType, Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                        game.addEffect(effect, source);
                    }
                    if (gainsHaste) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PutTokenOntoBattlefieldCopyTargetEffect copy() {
        return new PutTokenOntoBattlefieldCopyTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Put a token onto the battlefield that's a copy of ");
        if (mode.getTargets() != null) {
            sb.append(mode.getTargets().get(0).getTargetName());
        }
        return sb.toString();

    }

    public List<Permanent> getAddedPermanent() {
        return addedTokenPermanents;
    }
}
