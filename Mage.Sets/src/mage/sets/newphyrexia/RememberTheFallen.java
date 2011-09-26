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
package mage.sets.newphyrexia;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public class RememberTheFallen extends CardImpl<RememberTheFallen> {

    private static final FilterCreatureCard filterCreature = new FilterCreatureCard("creature card from your graveyard");
    private static final FilterArtifactCard filterArtifact = new FilterArtifactCard("artifact card from your graveyard");

    public RememberTheFallen(UUID ownerId) {
        super(ownerId, 21, "Remember the Fallen", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "NPH";

        this.color.setWhite(true);

        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filterCreature));

        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(filterArtifact));
        this.getSpellAbility().addMode(mode);

        mode = new Mode();
        mode.getTargets().add(new TargetCardInYourGraveyard(filterCreature));
        mode.getTargets().add(new TargetCardInYourGraveyard(filterArtifact));
        mode.getEffects().add(new RememberTheFallenEffect());
        this.getSpellAbility().addMode(mode);
    }

    public RememberTheFallen(final RememberTheFallen card) {
        super(card);
    }

    @Override
    public RememberTheFallen copy() {
        return new RememberTheFallen(this);
    }
}

class RememberTheFallenEffect extends OneShotEffect<RememberTheFallenEffect> {

    public RememberTheFallenEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature card and target artifact card from your graveyard to your hand";
    }

    public RememberTheFallenEffect(final RememberTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public RememberTheFallenEffect copy() {
        return new RememberTheFallenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            List<UUID> targets = target.getTargets();
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getId(), game, true);
                }
            }
        }
        return true;
    }
}
