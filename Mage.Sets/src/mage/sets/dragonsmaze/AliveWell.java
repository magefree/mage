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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.SplitCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.CentaurToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AliveWell extends SplitCard<AliveWell> {

    public AliveWell(UUID ownerId) {
        super(ownerId, 121, "Alive", "Well", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}","{W}", true);
        this.expansionSetCode = "DGM";

        this.color.setGreen(true);
        this.color.setWhite(true);

        // Alive
        // Put a 3/3 green Centaur creature token onto the battlefield.
        getLeftHalfCard().getColor().setGreen(true);
        getLeftHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new CentaurToken()));


        // Well
        // You gain 2 life for each creature you control.
        getRightHalfCard().getColor().setWhite(true);
        getRightHalfCard().getSpellAbility().addEffect(new WellEffect());

    }

    public AliveWell(final AliveWell card) {
        super(card);
    }

    @Override
    public AliveWell copy() {
        return new AliveWell(this);
    }
}

class WellEffect extends OneShotEffect<WellEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public WellEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 2 life for each creature you control";
    }

    public WellEffect(final WellEffect effect) {
        super(effect);
    }

    @Override
    public WellEffect copy() {
        return new WellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int life = 2 * game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        if (player != null) {
            player.gainLife(life, game);
        }
        return true;
    }

}
