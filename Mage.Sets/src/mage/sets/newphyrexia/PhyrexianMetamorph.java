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

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public class PhyrexianMetamorph extends CardImpl<PhyrexianMetamorph> {

    public PhyrexianMetamorph (UUID ownerId) {
        super(ownerId, 42, "Phyrexian Metamorph", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{UP}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Shapeshifter");
		this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
		Ability ability = new EntersBattlefieldAbility(new EntersBattlefieldEffect(new PhyrexianMetamorphEffect()), "You may have {this} enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types");
        this.addAbility(ability);
    }

    public PhyrexianMetamorph (final PhyrexianMetamorph card) {
        super(card);
    }

    @Override
    public PhyrexianMetamorph copy() {
        return new PhyrexianMetamorph(this);
    }

}

class PhyrexianMetamorphEffect extends OneShotEffect<PhyrexianMetamorphEffect> {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.getCardType().add(CardType.CREATURE);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }
        
    public PhyrexianMetamorphEffect() {
        super(Outcome.Copy);
    }

    public PhyrexianMetamorphEffect(final PhyrexianMetamorphEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        //TODO: handle copying copies
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetPermanent(filter);
            if (target.canChoose(source.getControllerId(), game)) {
                player.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent perm = game.getPermanent(target.getFirstTarget());
                if (perm != null) {
                    perm = perm.copy();
                    perm.reset(game);
                    perm.assignNewId();
                    if (!perm.getCardType().contains(CardType.ARTIFACT))
                        perm.getCardType().add(CardType.ARTIFACT);
                    game.addEffect(new CopyEffect(perm), source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PhyrexianMetamorphEffect copy() {
        return new PhyrexianMetamorphEffect(this);
    }
    
}
