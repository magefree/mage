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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CutTheTethers extends CardImpl {

    public CutTheTethers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");


        // For each Spirit, return it to its owner's hand unless that player pays {3}.
        this.getSpellAbility().addEffect(new CutTheTethersEffect());
    }

    public CutTheTethers(final CutTheTethers card) {
        super(card);
    }

    @Override
    public CutTheTethers copy() {
        return new CutTheTethers(this);
    }
}

class CutTheTethersEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Spirit creatures");
    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public CutTheTethersEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "For each Spirit, return it to its owner's hand unless that player pays {3}";
    }

    public CutTheTethersEffect(final CutTheTethersEffect effect) {
        super(effect);
    }

    @Override
    public CutTheTethersEffect copy() {
        return new CutTheTethersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature: game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            Player player = game.getPlayer(creature.getControllerId());
            if (player != null) {
                boolean paid = false;
                if (player.chooseUse(outcome, new StringBuilder("Pay {3} to keep ").append(creature.getName()).append(" on the battlefield?").toString(), source, game)) {
                    Cost cost = new GenericManaCost(3);
                    if (!cost.pay(source, game, source.getSourceId(), creature.getControllerId(), false, null)) {
                        paid = true;
                    }
                    if (!paid) {
                        creature.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    }
                }
            }
        }
        return true;
    }
}
