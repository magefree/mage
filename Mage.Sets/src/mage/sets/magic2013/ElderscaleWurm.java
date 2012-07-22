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
package mage.sets.magic2013;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class ElderscaleWurm extends CardImpl<ElderscaleWurm> {

    public ElderscaleWurm(UUID ownerId) {
        super(ownerId, 167, "Elderscale Wurm", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.expansionSetCode = "M13";
        this.subtype.add("Wurm");

        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Elderscale Wurm enters the battlefield, if your life total is less than 7, your life total becomes 7.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ElderscaleWurmSetLifeEffect(), false));

        // As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ElderscaleWurmReplacementEffect()));
    }

    public ElderscaleWurm(final ElderscaleWurm card) {
        super(card);
    }

    @Override
    public ElderscaleWurm copy() {
        return new ElderscaleWurm(this);
    }
}

class ElderscaleWurmSetLifeEffect extends OneShotEffect<ElderscaleWurmSetLifeEffect> {

    public ElderscaleWurmSetLifeEffect() {
        super(Constants.Outcome.Benefit);
    }

    public ElderscaleWurmSetLifeEffect(final ElderscaleWurmSetLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player != null && player.getLife() < 7) {
            player.setLife(7, game);
        }

        return true;
    }

    @Override
    public ElderscaleWurmSetLifeEffect copy() {
        return new ElderscaleWurmSetLifeEffect(this);
    }

}

class ElderscaleWurmReplacementEffect extends ReplacementEffectImpl<ElderscaleWurmReplacementEffect> {

    public ElderscaleWurmReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead";
    }

    public ElderscaleWurmReplacementEffect(final ElderscaleWurmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ElderscaleWurmReplacementEffect copy() {
        return new ElderscaleWurmReplacementEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS)) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null && controller.getLife() >= 7
                        && (controller.getLife() - event.getAmount()) < 7
                        && event.getPlayerId().equals(controller.getId())) {
                    event.setAmount(controller.getLife() - 7);
                }
            }
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}
