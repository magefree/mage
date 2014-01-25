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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HuntersProwess extends CardImpl<HuntersProwess> {

    public HuntersProwess(UUID ownerId) {
        super(ownerId, 124, "Hunter's Prowess", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "BNG";

        this.color.setGreen(true);

        // Until end of turn, target creature gets +3/+3 and gains trample and "Whenever this creature deals combat damage to a player, draw that many cards."
        Effect effect = new BoostTargetEffect(3,3, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature gets +3/+3");
        this.getSpellAbility().addEffect(effect);
        Ability grantedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new HuntersProwessDrawEffect(), false);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(grantedAbility, Duration.EndOfTurn,
                "and gains trample and \"Whenever this creature deals combat damage to a player, draw that many cards.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public HuntersProwess(final HuntersProwess card) {
        super(card);
    }

    @Override
    public HuntersProwess copy() {
        return new HuntersProwess(this);
    }
}

class HuntersProwessDrawEffect extends OneShotEffect<HuntersProwessDrawEffect> {

    public HuntersProwessDrawEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw that many cards";
    }

    public HuntersProwessDrawEffect(final HuntersProwessDrawEffect effect) {
        super(effect);
    }

    @Override
    public HuntersProwessDrawEffect copy() {
        return new HuntersProwessDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) this.getValue("damage");
            if (damage > 0) {
                controller.drawCards(damage, game);
            }
            return true;
        }


        return false;
    }
}