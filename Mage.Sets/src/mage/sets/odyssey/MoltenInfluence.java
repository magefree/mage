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
package mage.sets.odyssey;

import java.util.UUID;
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
 * @author cbt33, LevelX2 (Quash)
 */
public class MoltenInfluence extends CardImpl<MoltenInfluence> {

        private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        Predicates.or(
                new CardTypePredicate(CardType.INSTANT), 
                new CardTypePredicate(CardType.SORCERY));
    }
    
    
    public MoltenInfluence(UUID ownerId) {
        super(ownerId, 207, "Molten Influence", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Counter target instant or sorcery spell unless its controller has Molten Influence deal 4 damage to him or her.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new MoltenInfluenceEffect());
        
    }

    public MoltenInfluence(final MoltenInfluence card) {
        super(card);
    }

    @Override
    public MoltenInfluence copy() {
        return new MoltenInfluence(this);
    }
}


class MoltenInfluenceEffect extends OneShotEffect<MoltenInfluenceEffect> {
    
    public MoltenInfluenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target instant or sorcery spell unless its controller has Molten Influence deal 4 damage to him or her.";
    }
    
    public MoltenInfluenceEffect(final MoltenInfluenceEffect effect){
        super(effect);
    }
    
    @Override
    public MoltenInfluenceEffect copy() {
        return new MoltenInfluenceEffect(this);
    }

@Override
public boolean apply(Game game, Ability source) {
    Spell spell = game.getStack().getSpell(source.getFirstTarget());
    if (spell!=null) {
    Player player = game.getPlayer(spell.getOwnerId());
    String message = "Have Molten Influence do 4 damage to you?";
    if (player.chooseUse(Outcome.Damage, message, game)){
        player.damage(4, source.getSourceId(), game, false, true);
    } else {
        spell.counter(source.getId(), game);
    }
    }
    return false;
}

}
