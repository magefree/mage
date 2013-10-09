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
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class KithkinZealot extends CardImpl<KithkinZealot> {

    public KithkinZealot(UUID ownerId) {
        super(ownerId, 9, "Kithkin Zealot", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Kithkin");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Kithkin Zealot enters the battlefield, you gain 1 life for each black and/or red permanent target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KithkinZealotEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public KithkinZealot(final KithkinZealot card) {
        super(card);
    }

    @Override
    public KithkinZealot copy() {
        return new KithkinZealot(this);
    }
}

class KithkinZealotEffect extends OneShotEffect<KithkinZealotEffect> {

    public KithkinZealotEffect() {
        super(Outcome.Neutral);
        this.staticText = "you gain 1 life for each black and/or red permanent target opponent controls";
    }

    public KithkinZealotEffect(final KithkinZealotEffect effect) {
        super(effect);
    }

    @Override
    public KithkinZealotEffect copy() {
        return new KithkinZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
        if (opponent != null) {
            int amount = game.getBattlefield().countAll(filter, opponent.getId(), game);
            if (you != null) {
                you.gainLife(amount, game);
                return true;
            }
            
        }
        return false;
    }
}