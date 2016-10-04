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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author MarcoMarin
 */
public class YawgmothDemon extends CardImpl {

    public YawgmothDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add("Demon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // At the beginning of your upkeep, you may sacrifice an artifact. If you don't, tap Yawgmoth Demon and it deals 2 damage to you.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new YawgmothDemonEffect(), TargetController.YOU, false);
        this.addAbility(ability);
    }

    public YawgmothDemon(final YawgmothDemon card) {
        super(card);
    }

    @Override
    public YawgmothDemon copy() {
        return new YawgmothDemon(this);
    }
}

class YawgmothDemonEffect extends OneShotEffect {

    public YawgmothDemonEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may sacrifice an artifact. If you don't, tap {this} and it deals 2 damage to you";
    }

    public YawgmothDemonEffect(final YawgmothDemonEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothDemonEffect copy() {
        return new YawgmothDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int artifacts = game.getBattlefield().countAll(new FilterArtifactPermanent(), source.getControllerId(), game);
            boolean artifactSacrificed = false;
            if (artifacts > 0 && controller.chooseUse(outcome, "Sacrifice an artifact?", source, game)) {
                if (new SacrificeControllerEffect(new FilterArtifactPermanent(), 1, "").apply(game, source)) {
                    artifactSacrificed = true;
                }
            }
            if (!artifactSacrificed) {
                Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
                if (sourceObject != null) {
                    sourceObject.tap(game);
                    controller.damage(2, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
