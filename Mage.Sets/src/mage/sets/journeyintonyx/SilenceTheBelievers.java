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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;



/**
 *
 * @author LevelX2
 */
public class SilenceTheBelievers extends CardImpl<SilenceTheBelievers> {

    public SilenceTheBelievers(UUID ownerId) {
        super(ownerId, 82, "Silence the Believers", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");
        this.expansionSetCode = "JOU";

        this.color.setBlack(true);

        // Strive - Silence the Believers costs 2B more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{B}"));
        // Exile any number of target creatures and all Auras attached to them.
        this.getSpellAbility().addEffect(new SilenceTheBelieversExileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));

    }

    public SilenceTheBelievers(final SilenceTheBelievers card) {
        super(card);
    }

    @Override
    public SilenceTheBelievers copy() {
        return new SilenceTheBelievers(this);
    }
}

class SilenceTheBelieversExileEffect extends OneShotEffect<SilenceTheBelieversExileEffect> {

    public SilenceTheBelieversExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile any number of target creatures and all Auras attached to them";
    }

    public SilenceTheBelieversExileEffect(final SilenceTheBelieversExileEffect effect) {
        super(effect);
    }

    @Override
    public SilenceTheBelieversExileEffect copy() {
        return new SilenceTheBelieversExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID targetId: this.getTargetPointer().getTargets(null, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    for (UUID attachmentId: creature.getAttachments()) {
                        Permanent attachment = game.getPermanent(attachmentId);
                        if (attachment != null && attachment.getSubtype().contains("Aura")) {
                            controller.moveCardToExileWithInfo(attachment, null, null, source.getSourceId(), game, Zone.BATTLEFIELD);
                        }
                    }
                    controller.moveCardToExileWithInfo(creature, null, null, source.getSourceId(), game, Zone.BATTLEFIELD);
                }
            }
            return true;
        }
        return false;
    }
}
