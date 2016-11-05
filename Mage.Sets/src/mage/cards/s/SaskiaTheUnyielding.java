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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SaskiaTheUnyielding extends CardImpl {

    public SaskiaTheUnyielding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}{W}");

        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // As Saskia the Unyielding enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Damage)));
        // Whenever a creature you control deals combat damage to a player, it deals that much damage to the chosen player.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new SaskiaTheUnyieldingEffect(),
                new FilterControlledCreaturePermanent("a creature you control"), false, SetTargetPointer.NONE, true
        ));
    }

    public SaskiaTheUnyielding(final SaskiaTheUnyielding card) {
        super(card);
    }

    @Override
    public SaskiaTheUnyielding copy() {
        return new SaskiaTheUnyielding(this);
    }
}

class SaskiaTheUnyieldingEffect extends OneShotEffect {

    public SaskiaTheUnyieldingEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to the chosen player";
    }

    public SaskiaTheUnyieldingEffect(final SaskiaTheUnyieldingEffect effect) {
        super(effect);
    }

    @Override
    public SaskiaTheUnyieldingEffect copy() {
        return new SaskiaTheUnyieldingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
            Player player = game.getPlayer(playerId);
            if (player != null && player.canRespond()) {
                player.damage((Integer) this.getValue("damage"), source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
