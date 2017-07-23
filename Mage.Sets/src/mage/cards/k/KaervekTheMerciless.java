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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public class KaervekTheMerciless extends CardImpl {

    public KaervekTheMerciless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever an opponent casts a spell, Kaervek the Merciless deals damage to target creature or player equal to that spell's converted mana cost.
        Ability ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new KaervekTheMercilessEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.SPELL);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public KaervekTheMerciless(final KaervekTheMerciless card) {
        super(card);
    }

    @Override
    public KaervekTheMerciless copy() {
        return new KaervekTheMerciless(this);
    }
}

class KaervekTheMercilessEffect extends OneShotEffect {

    public KaervekTheMercilessEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage to target creature or player equal to that spell's converted mana cost";
    }

    public KaervekTheMercilessEffect(final KaervekTheMercilessEffect effect) {
        super(effect);
    }

    @Override
    public KaervekTheMercilessEffect copy() {
        return new KaervekTheMercilessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject spellCast = game.getObject(getTargetPointer().getFirst(game, source));
        if (spellCast instanceof Spell) {
            int cost = ((Spell) spellCast).getConvertedManaCost();
            Player target = game.getPlayer(source.getFirstTarget());
            if (target != null) {
                target.damage(cost, source.getSourceId(), game, false, true);
                return true;
            }
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(cost, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
