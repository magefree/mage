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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth

 */
public class MirrorSheen extends CardImpl<MirrorSheen> {
    
    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
        filter.add(new TargetYouPredicate());
    }

    public MirrorSheen(UUID ownerId) {
        super(ownerId, 105, "Mirror Sheen", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U/R}{U/R}");
        this.expansionSetCode = "EVE";

        this.color.setRed(true);
        this.color.setBlue(true);

        // {1}{UR}{UR}: Copy target instant or sorcery spell that targets you. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirrorSheenEffect(), new ManaCostsImpl("{1}{U/R}{U/R}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
        
    }

    public MirrorSheen(final MirrorSheen card) {
        super(card);
    }

    @Override
    public MirrorSheen copy() {
        return new MirrorSheen(this);
    }
}

class MirrorSheenEffect extends OneShotEffect<MirrorSheenEffect> {

    public MirrorSheenEffect() {
        super(Outcome.Copy);
        staticText = "Copy target instant or sorcery spell that targets you.  You may choose new targets for the copy";
    }

    public MirrorSheenEffect(final MirrorSheenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
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
    public MirrorSheenEffect copy() {
        return new MirrorSheenEffect(this);
    }
}

class TargetYouPredicate implements ObjectPlayerPredicate<ObjectPlayer<StackObject>> {

    @Override
    public boolean apply(ObjectPlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }

        for (Target target : input.getObject().getStackAbility().getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (controllerId.equals(targetId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you";
    }
}