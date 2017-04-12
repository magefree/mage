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
package mage.cards.o;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class OrzhovCharm extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with converted mana cost 1 or less from your graveyard");
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public OrzhovCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{B}");


        //Choose one - Return target creature you control and all Auras you control attached to it to their owner's hand
        this.getSpellAbility().addEffect(new OrzhovCharmReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // or destroy target creature and you lose life equal to its toughness;
        Mode mode = new Mode();
        mode.getEffects().add(new OrzhovCharmDestroyAndLoseLifeEffect());
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // or return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode2.getTargets().add(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode2);


    }

    public OrzhovCharm(final OrzhovCharm card) {
        super(card);
    }

    @Override
    public OrzhovCharm copy() {
        return new OrzhovCharm(this);
    }
}

class OrzhovCharmReturnToHandEffect extends OneShotEffect {

    public OrzhovCharmReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and all Auras you control attached to it to their owner's hand";
    }

    public OrzhovCharmReturnToHandEffect(final OrzhovCharmReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovCharmReturnToHandEffect copy() {
        return new OrzhovCharmReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            LinkedList<UUID> attachments = new LinkedList<>();
            attachments.addAll(target.getAttachments());
            for (UUID attachmentId : attachments) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getControllerId().equals(source.getControllerId())
                        && attachment.getSubtype(game).contains("Aura")) {
                    attachment.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
            if (target.getControllerId().equals(source.getControllerId())) {
                target.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}

class OrzhovCharmDestroyAndLoseLifeEffect extends OneShotEffect {

    public OrzhovCharmDestroyAndLoseLifeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target creature and you lose life equal to its toughness";
    }

    public OrzhovCharmDestroyAndLoseLifeEffect(final OrzhovCharmDestroyAndLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovCharmDestroyAndLoseLifeEffect copy() {
        return new OrzhovCharmDestroyAndLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (target != null && controller != null) {
            int toughness = target.getToughness().getValue();
            target.destroy(source.getSourceId(), game, false);
            if (toughness > 0) {
                controller.loseLife(toughness, game, false);
            }
            return true;
        }
        return false;
    }
}
