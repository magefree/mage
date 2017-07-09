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
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class LilianasDefeat extends CardImpl {

    public LilianasDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("black creature or black planeswalker");
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        // Destroy target black creature or black planeswalker. If that permanent was a Liliana planeswalker, her controller loses 3 life.
        this.getSpellAbility().addEffect(new LilianasDefeatEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public LilianasDefeat(final LilianasDefeat card) {
        super(card);
    }

    @Override
    public LilianasDefeat copy() {
        return new LilianasDefeat(this);
    }
}

class LilianasDefeatEffect extends OneShotEffect {

    public LilianasDefeatEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target black creature or black planeswalker. If that permanent was a Liliana planeswalker, her controller loses 3 life";
    }

    public LilianasDefeatEffect(final LilianasDefeatEffect effect) {
        super(effect);
    }

    @Override
    public LilianasDefeatEffect copy() {
        return new LilianasDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            permanent.destroy(source.getSourceId(), game, true);
            game.applyEffects();
            if (permanent.isPlaneswalker() && permanent.getSubtype(game).contains(SubType.LILIANA.getDescription())) {
                Player permanentController = game.getPlayer(permanent.getControllerId());
                if (permanentController != null) {
                    permanentController.loseLife(3, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
