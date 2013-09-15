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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class MeletisCharlatan extends CardImpl<MeletisCharlatan> {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MeletisCharlatan(UUID ownerId) {
        super(ownerId, 54, "Meletis Charlatan", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "THS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{U}, {T}: The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy.
        this.getSpellAbility().addEffect(new MeletisCharlatanCopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public MeletisCharlatan(final MeletisCharlatan card) {
        super(card);
    }

    @Override
    public MeletisCharlatan copy() {
        return new MeletisCharlatan(this);
    }
}

class MeletisCharlatanCopyTargetSpellEffect extends OneShotEffect<MeletisCharlatanCopyTargetSpellEffect> {

    public MeletisCharlatanCopyTargetSpellEffect() {
        super(Outcome.Copy);
        staticText = "The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy";
    }

    public MeletisCharlatanCopyTargetSpellEffect(final MeletisCharlatanCopyTargetSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Spell copy = spell.copySpell();
            copy.setControllerId(spell.getControllerId());
            copy.setCopiedSpell(true);
            game.getStack().push(copy);
            copy.chooseNewTargets(game, spell.getControllerId());
            Player player = game.getPlayer(spell.getControllerId());
            String activateMessage = copy.getActivatedMessage(game);
            if (activateMessage.startsWith(" casts ")) {
                activateMessage = activateMessage.substring(6);
            }
            game.informPlayers(player.getName() + " copies " + activateMessage);;
            return true;
        }
        return false;
    }

    @Override
    public MeletisCharlatanCopyTargetSpellEffect copy() {
        return new MeletisCharlatanCopyTargetSpellEffect(this);
    }

}
