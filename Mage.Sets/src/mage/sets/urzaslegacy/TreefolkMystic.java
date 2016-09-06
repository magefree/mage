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
package mage.sets.urzaslegacy;

import java.util.LinkedList;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BlocksOrBecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class TreefolkMystic extends CardImpl {

    public TreefolkMystic(UUID ownerId) {
        super(ownerId, 114, "Treefolk Mystic", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Treefolk");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Treefolk Mystic blocks or becomes blocked by a creature, destroy all Auras attached to that creature.
        this.addAbility(new BlocksOrBecomesBlockedByCreatureTriggeredAbility(new TreefolkMysticEffect(), false));
    }

    public TreefolkMystic(final TreefolkMystic card) {
        super(card);
    }

    @Override
    public TreefolkMystic copy() {
        return new TreefolkMystic(this);
    }
}

class TreefolkMysticEffect extends OneShotEffect {



    public TreefolkMysticEffect() {
        super(Outcome.DestroyPermanent);
    }

    public TreefolkMysticEffect(final TreefolkMysticEffect effect) {
        super(effect);
    }

    @Override
    public TreefolkMysticEffect copy() {
        return new TreefolkMysticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null)
        {
            LinkedList<UUID> attachments = new LinkedList();
            attachments.addAll(permanent.getAttachments());
            for(UUID uuid : attachments)
            {
                Permanent aura = game.getPermanent(uuid);
                if(aura != null && aura.getSubtype(game).contains("Aura"))
                {
                    aura.destroy(source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "destroy all Auras attached to that creature";
    }

}
