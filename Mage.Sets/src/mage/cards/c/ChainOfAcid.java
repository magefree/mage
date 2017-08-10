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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class ChainOfAcid extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public ChainOfAcid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Destroy target noncreature permanent. Then that permanent's controller may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfAcidEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public ChainOfAcid(final ChainOfAcid card) {
        super(card);
    }

    @Override
    public ChainOfAcid copy() {
        return new ChainOfAcid(this);
    }
}

class ChainOfAcidEffect extends OneShotEffect {

    ChainOfAcidEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target noncreature permanent. Then that permanent's controller may copy this spell and may choose a new target for that copy.";
    }

    ChainOfAcidEffect(final ChainOfAcidEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfAcidEffect copy() {
        return new ChainOfAcidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = source.getFirstTarget();
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                Player affectedPlayer = game.getPlayer(permanent.getControllerId());
                if (affectedPlayer != null) {
                    Effect effect = new DestroyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(targetId));
                    effect.apply(game, source);
                    if (affectedPlayer.chooseUse(Outcome.Copy, "Copy the spell?", source, game)) {
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
                            game.informPlayers(affectedPlayer.getLogName() + " copies " + spell.getName() + '.');
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
