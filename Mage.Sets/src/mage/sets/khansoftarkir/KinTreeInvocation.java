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
package mage.sets.khansoftarkir;

import java.util.ArrayList;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class KinTreeInvocation extends CardImpl {

    public KinTreeInvocation(UUID ownerId) {
        super(ownerId, 183, "Kin-Tree Invocation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}{G}");
        this.expansionSetCode = "KTK";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // Put an X/X black and green Spirit Warrior creature token onto the battlefield, where X is the greatest toughness among creatures you control.
        this.getSpellAbility().addEffect(new KinTreeInvocationCreateTokenEffect());
        
    }

    public KinTreeInvocation(final KinTreeInvocation card) {
        super(card);
    }

    @Override
    public KinTreeInvocation copy() {
        return new KinTreeInvocation(this);
    }
}

class KinTreeInvocationCreateTokenEffect extends OneShotEffect {

    public KinTreeInvocationCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put an X/X black and green Spirit Warrior creature token onto the battlefield, where X is the greatest toughness among creatures you control";
    }

    public KinTreeInvocationCreateTokenEffect(final KinTreeInvocationCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public KinTreeInvocationCreateTokenEffect copy() {
        return new KinTreeInvocationCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = Integer.MIN_VALUE;
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (value < permanent.getToughness().getValue()) {
                value = permanent.getToughness().getValue();
            }
        }

        ArrayList<String> list = new ArrayList<>();
        list.add("Spirit");
        list.add("Warrior");
        ObjectColor objectColor = new ObjectColor();
        objectColor.setBlack(true);
        objectColor.setGreen(true);
        Token token = new Token("Spirit Warrior", "X/X black and green Spirit Warrior creature token onto the battlefield, where X is the greatest toughness among creatures you control",
                objectColor, list, value, value, new AbilitiesImpl<>());
        token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }

}
