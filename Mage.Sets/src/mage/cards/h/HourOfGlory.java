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
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HourOfGlory extends CardImpl {

    public HourOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature. If that creature was a God, its controller reveals his or her hand and exiles all cards with the same name as that creature.
        this.getSpellAbility().addEffect(new HourOfGloryEffect());
    }

    public HourOfGlory(final HourOfGlory card) {
        super(card);
    }

    @Override
    public HourOfGlory copy() {
        return new HourOfGlory(this);
    }
}

class HourOfGloryEffect extends OneShotEffect {

    public HourOfGloryEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature. If that creature was a God, its controller reveals his or her hand and exiles all cards with the same name as that creature";
    }

    public HourOfGloryEffect(final HourOfGloryEffect effect) {
        super(effect);
    }

    @Override
    public HourOfGloryEffect copy() {
        return new HourOfGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                controller.moveCards(targetCreature, Zone.EXILED, source, game);
                if (targetCreature.hasSubtype(SubType.GOD.getDescription(), game)) {
                    game.applyEffects();
                    Player targetController = game.getPlayer(targetCreature.getControllerId());
                    if (targetController != null) {
                        targetController.revealCards(sourceObject.getIdName(), targetController.getHand(), game);
                        Set<Card> toExile = new HashSet<>();
                        for (Card card : targetController.getHand().getCards(game)) {
                            if (card.getName().equals(targetCreature.getName())) {
                                toExile.add(card);
                            }
                        }
                        targetController.moveCards(toExile, Zone.EXILED, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
