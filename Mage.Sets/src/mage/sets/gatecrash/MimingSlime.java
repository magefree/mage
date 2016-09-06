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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MimingSlime extends CardImpl {

    public MimingSlime(UUID ownerId) {
        super(ownerId, 126, "Miming Slime", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "GTC";

        // Put an X/X green Ooze creature token onto the battlefield, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new MimingSlimeEffect());
    }

    public MimingSlime(final MimingSlime card) {
        super(card);
    }

    @Override
    public MimingSlime copy() {
        return new MimingSlime(this);
    }
}

class MimingSlimeEffect extends OneShotEffect {

    public MimingSlimeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put an X/X green Ooze creature token onto the battlefield, where X is the greatest power among creatures you control";
    }

    public MimingSlimeEffect(final MimingSlimeEffect effect) {
        super(effect);
    }

    @Override
    public MimingSlimeEffect copy() {
        return new MimingSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game);
            int amount = 0;
            for (Permanent creature : creatures) {
                int power = creature.getPower().getValue();
                if (amount < power) {
                    amount = power;
                }
            }
            OozeToken oozeToken = new OozeToken();
            oozeToken.getPower().modifyBaseValue(amount);
            oozeToken.getToughness().modifyBaseValue(amount);
            oozeToken.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}

class OozeToken extends Token {
    public OozeToken() {
        super("Ooze", "X/X green Ooze creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Ooze");
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
        setOriginalExpansionSetCode("RTR");
    }
}
