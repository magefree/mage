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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ClovenCasting extends CardImpl<ClovenCasting> {

    private static final FilterSpell filter = new FilterSpell("a multicolored instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filter.add(new MulticoloredPredicate());
    }

    public ClovenCasting(UUID ownerId) {
        super(ownerId, 86, "Cloven Casting", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{R}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlue(true);

        // Whenever you cast a multicolored instant or sorcery spell, you may pay {1}. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastTriggeredAbility(new DoIfCostPaid(new ClovenCastingEffect(), new GenericManaCost(1)), filter, true, true));

    }

    public ClovenCasting(final ClovenCasting card) {
        super(card);
    }

    @Override
    public ClovenCasting copy() {
        return new ClovenCasting(this);
    }
}

class ClovenCastingEffect extends OneShotEffect<ClovenCastingEffect> {

    public ClovenCastingEffect() {
        super(Outcome.Copy);
        staticText = "copy that spell. You may choose new targets for the copy";
    }

    public ClovenCastingEffect(final ClovenCastingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Spell copy = spell.copySpell();
            copy.setControllerId(source.getControllerId());
            copy.setCopiedSpell(true);
            game.getStack().push(copy);
            copy.chooseNewTargets(game, source.getControllerId());
            Player player = game.getPlayer(source.getControllerId());
            String activateMessage = copy.getActivatedMessage(game);
            if (activateMessage.startsWith(" casts ")) {
                activateMessage = activateMessage.substring(6);
            }
            game.informPlayers(player.getName() + " copies " + activateMessage);
            return true;
        }
        return false;
    }

    @Override
    public ClovenCastingEffect copy() {
        return new ClovenCastingEffect(this);
    }
}