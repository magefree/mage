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
package mage.sets.nemesis;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ParallaxWave extends CardImpl {

    public ParallaxWave(UUID ownerId) {
        super(ownerId, 17, "Parallax Wave", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "NMS";

        this.color.setWhite(true);

        // Fading 5
        this.addAbility(new FadingAbility(5, this));
        
        // Remove a fade counter from Parallax Wave: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Parallax Wave leaves the battlefield, each player returns to the battlefield all cards he or she owns exiled with Parallax Wave.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ParallaxWaveEffect(), false));

    }

    public ParallaxWave(final ParallaxWave card) {
        super(card);
    }

    @Override
    public ParallaxWave copy() {
        return new ParallaxWave(this);
    }
}

class ParallaxWaveEffect extends OneShotEffect {

    public ParallaxWaveEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player returns to the battlefield all cards he or she owns exiled with {this}";
    }

    public ParallaxWaveEffect(final ParallaxWaveEffect effect) {
        super(effect);
    }

    @Override
    public ParallaxWaveEffect copy() {
        return new ParallaxWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            UUID exileZoneId = CardUtil.getObjectExileZoneId(game, sourceObject);
            if (exileZoneId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                if (exileZone != null) {
                    for (Card card: exileZone.getCards(game)) {
                        Player player = game.getPlayer(card.getOwnerId());
                        if (player != null) {
                            player.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
                        }
                    }
                    exileZone.clear();
                }
                return true;                
            }
        }
        return false;
    }
}
