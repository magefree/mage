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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class SpellCrumple extends CardImpl {

    public SpellCrumple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard. Put Spell Crumple on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new SpellCrumpleCounterEffect());
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    public SpellCrumple(final SpellCrumple card) {
        super(card);
    }

    @Override
    public SpellCrumple copy() {
        return new SpellCrumple(this);
    }
}

class SpellCrumpleCounterEffect extends OneShotEffect {

    public SpellCrumpleCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard";
    }

    public SpellCrumpleCounterEffect(final SpellCrumpleCounterEffect effect) {
        super(effect);
    }

    @Override
    public SpellCrumpleCounterEffect copy() {
        return new SpellCrumpleCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return game.getStack().counter(targetPointer.getFirst(game, source), source.getSourceId(), game, Zone.LIBRARY, false, ZoneDetail.BOTTOM);
        }
        return false;
    }
}
