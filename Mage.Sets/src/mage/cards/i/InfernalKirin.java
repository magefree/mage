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
package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class InfernalKirin extends CardImpl {

    public InfernalKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Kirin");
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, target player reveals his or her hand and discards all cards with that spell's converted mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new InfernalKirinEffect(), StaticFilters.SPIRIT_OR_ARCANE_CARD, false, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public InfernalKirin(final InfernalKirin card) {
        super(card);
    }

    @Override
    public InfernalKirin copy() {
        return new InfernalKirin(this);
    }
}

class InfernalKirinEffect extends OneShotEffect {

    public InfernalKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player reveals his or her hand and discards all cards with that spell's converted mana cost";
    }

    public InfernalKirinEffect(final InfernalKirinEffect effect) {
        super(effect);
    }

    @Override
    public InfernalKirinEffect copy() {
        return new InfernalKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getConvertedManaCost();
            Player targetPlayer = null;
            for(Target target: source.getTargets()) {
                if (target instanceof TargetPlayer) {
                    targetPlayer = game.getPlayer(target.getFirstTarget());
                }
            }
            if (targetPlayer != null) {
                if (!targetPlayer.getHand().isEmpty()) {
                    targetPlayer.revealCards("Infernal Kirin", targetPlayer.getHand(), game);
                    for (UUID uuid: targetPlayer.getHand().copy()) {
                        Card card = game.getCard(uuid);
                        if (card != null && card.getConvertedManaCost() == cmc) {
                            targetPlayer.discard(card, source, game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
