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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class SoulScourge extends CardImpl<SoulScourge> {

    public SoulScourge(UUID ownerId) {
        super(ownerId, 85, "Soul Scourge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Nightmare");
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Soul Scourge enters the battlefield, target player loses 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SoulScourgeEntersEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        // When Soul Scourge leaves the battlefield, that player gains 3 life.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SoulScourgeLeavesEffect(), false));
    }

    public SoulScourge(final SoulScourge card) {
        super(card);
    }

    @Override
    public SoulScourge copy() {
        return new SoulScourge(this);
    }
}

class SoulScourgeEntersEffect extends OneShotEffect<SoulScourgeEntersEffect> {

    public SoulScourgeEntersEffect() {
        super(Outcome.LoseLife);
        this.staticText = "target player loses 3 life";
    }

    public SoulScourgeEntersEffect(final SoulScourgeEntersEffect effect) {
        super(effect);
    }

    @Override
    public SoulScourgeEntersEffect copy() {
        return new SoulScourgeEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.loseLife(3, game);
            game.getState().setValue(CardUtil.getCardZoneString("targetPlayer", source.getSourceId(), game), player.getId());
            return true;
        }
        return false;
    }
}

class SoulScourgeLeavesEffect extends OneShotEffect<SoulScourgeLeavesEffect> {

    public SoulScourgeLeavesEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player gains 3 life";
    }

    public SoulScourgeLeavesEffect(final SoulScourgeLeavesEffect effect) {
        super(effect);
    }

    @Override
    public SoulScourgeLeavesEffect copy() {
        return new SoulScourgeLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(CardUtil.getCardZoneString("targetPlayer", source.getSourceId(), game));
        if (object instanceof UUID) {
            Player player = game.getPlayer((UUID) object);
            if (player != null) {
                player.gainLife(3, game);
                return true;
            }
        }
        return false;
    }
}
