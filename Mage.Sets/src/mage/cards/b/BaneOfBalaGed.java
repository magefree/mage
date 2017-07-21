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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class BaneOfBalaGed extends CardImpl {

    public BaneOfBalaGed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Whenever Bane of Bala Ged attacks, defending player exiles two permanents he or she controls.
        this.addAbility(new AttacksTriggeredAbility(new BaneOfBalaGedEffect(), false, "", SetTargetPointer.PLAYER));
    }

    public BaneOfBalaGed(final BaneOfBalaGed card) {
        super(card);
    }

    @Override
    public BaneOfBalaGed copy() {
        return new BaneOfBalaGed(this);
    }
}

class BaneOfBalaGedEffect extends OneShotEffect {

    public BaneOfBalaGedEffect() {
        super(Outcome.Benefit);
        this.staticText = "defending player exiles two permanents he or she controls";
    }

    public BaneOfBalaGedEffect(final BaneOfBalaGedEffect effect) {
        super(effect);
    }

    @Override
    public BaneOfBalaGedEffect copy() {
        return new BaneOfBalaGedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (defendingPlayer != null) {
            Target target = new TargetControlledPermanent(2);
            defendingPlayer.chooseTarget(outcome, target, source, game);
            Set<Card> toExile = new HashSet<>();
            target.getTargets().stream().map((targetId)
                    -> game.getPermanent(targetId)).filter((permanent)
                    -> (permanent != null)).forEach((permanent)
                    -> {
                toExile.add(permanent);
            });
            defendingPlayer.moveCards(toExile, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
