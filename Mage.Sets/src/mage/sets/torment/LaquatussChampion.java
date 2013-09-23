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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LaquatussChampion extends CardImpl<LaquatussChampion> {

    public LaquatussChampion(UUID ownerId) {
        super(ownerId, 67, "Laquatus's Champion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Nightmare");
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // When Laquatus's Champion enters the battlefield, target player loses 6 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LaquatussChampionEntersEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        // When Laquatus's Champion leaves the battlefield, that player gains 6 life.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LaquatussChampionLeavesEffect(), false));
        // {B}: Regenerate Laquatus's Champion.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
    }

    public LaquatussChampion(final LaquatussChampion card) {
        super(card);
    }

    @Override
    public LaquatussChampion copy() {
        return new LaquatussChampion(this);
    }
}

class LaquatussChampionEntersEffect extends OneShotEffect<LaquatussChampionEntersEffect> {

    public LaquatussChampionEntersEffect() {
        super(Outcome.LoseLife);
        this.staticText = "target player loses 6 life";
    }

    public LaquatussChampionEntersEffect(final LaquatussChampionEntersEffect effect) {
        super(effect);
    }

    @Override
    public LaquatussChampionEntersEffect copy() {
        return new LaquatussChampionEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.loseLife(6, game);
            game.getState().setValue(CardUtil.getCardZoneString("targetPlayer", source.getSourceId(), game), player.getId());
            return true;
        }
        return false;
    }
}

class LaquatussChampionLeavesEffect extends OneShotEffect<LaquatussChampionLeavesEffect> {

    public LaquatussChampionLeavesEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player gains 6 life";
    }

    public LaquatussChampionLeavesEffect(final LaquatussChampionLeavesEffect effect) {
        super(effect);
    }

    @Override
    public LaquatussChampionLeavesEffect copy() {
        return new LaquatussChampionLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(CardUtil.getCardZoneString("targetPlayer", source.getSourceId(), game));
        if (object instanceof UUID) {
            Player player = game.getPlayer((UUID) object);
            if (player != null) {
                player.gainLife(6, game);
                return true;
            }
        }
        return false;
    }
}
