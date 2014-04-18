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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Hubris extends CardImpl<Hubris> {

    public Hubris(UUID ownerId) {
        super(ownerId, 41, "Hubris", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "JOU";

        this.color.setBlue(true);

        // Return target creature and all Auras attached to it to their owners' hand.
        this.getSpellAbility().addEffect(new HubrisReturnEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));


    }

    public Hubris(final Hubris card) {
        super(card);
    }

    @Override
    public Hubris copy() {
        return new Hubris(this);
    }
}

class HubrisReturnEffect extends OneShotEffect<HubrisReturnEffect> {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public HubrisReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target creature and all Auras attached to it to their owners' hand";
    }

    public HubrisReturnEffect(final HubrisReturnEffect effect) {
        super(effect);
    }

    @Override
    public HubrisReturnEffect copy() {
        return new HubrisReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID targetId: targetPointer.getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    controller.moveCardToHandWithInfo(creature, source.getSourceId(), game, Zone.BATTLEFIELD);
                    for (UUID attachementId: creature.getAttachments()) {
                        Permanent attachment = game.getPermanent(attachementId);
                        if (attachment != null && filter.match(attachment, game)) {
                            controller.moveCardToHandWithInfo(attachment, source.getSourceId(), game, Zone.BATTLEFIELD);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
