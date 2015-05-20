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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author emerald000
 */
public class Dovescape extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a noncreature spell");
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public Dovescape(UUID ownerId) {
        super(ownerId, 143, "Dovescape", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W/U}{W/U}{W/U}");
        this.expansionSetCode = "DIS";


        // Whenever a player casts a noncreature spell, counter that spell. That player puts X 1/1 white and blue Bird creature tokens with flying onto the battlefield, where X is the spell's converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(new DovescapeEffect(), filter, false, true));
    }

    public Dovescape(final Dovescape card) {
        super(card);
    }

    @Override
    public Dovescape copy() {
        return new Dovescape(this);
    }
}

class DovescapeEffect extends OneShotEffect {
    
    DovescapeEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter that spell. That player puts X 1/1 white and blue Bird creature tokens with flying onto the battlefield, where X is the spell's converted mana cost";
    }
    
    DovescapeEffect(final DovescapeEffect effect) {
        super(effect);
    }
    
    @Override
    public DovescapeEffect copy() {
        return new DovescapeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        int spellCMC = 0;
        UUID spellControllerID = null;
        if (spell != null) {
            spellCMC = spell.getConvertedManaCost();
            spellControllerID = spell.getControllerId();
            game.getStack().counter(spell.getId(), source.getSourceId(), game);
        }
        Token token = new DovescapeToken();
        token.putOntoBattlefield(spellCMC, game, source.getSourceId(), spellControllerID);
        return true;
    }
}

class DovescapeToken extends Token {
    DovescapeToken() {
        super("Bird", "1/1 white and blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        subtype.add("Bird");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}
