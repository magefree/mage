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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class JinGitaxiasCoreAugur extends CardImpl<JinGitaxiasCoreAugur> {

    public JinGitaxiasCoreAugur(UUID ownerId) {
        super(ownerId, 37, "Jin-Gitaxias, Core Augur", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{8}{U}{U}");
        this.expansionSetCode = "NPH";
        this.supertype.add("Legendary");
        this.subtype.add("Praetor");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new DrawCardControllerEffect(7), false));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new JinGitaxiasCoreAugurEffect()));
    }

    public JinGitaxiasCoreAugur(final JinGitaxiasCoreAugur card) {
        super(card);
    }

    @Override
    public JinGitaxiasCoreAugur copy() {
        return new JinGitaxiasCoreAugur(this);
    }
}

class JinGitaxiasCoreAugurEffect extends ContinuousEffectImpl<JinGitaxiasCoreAugurEffect> {
    JinGitaxiasCoreAugurEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.PlayerEffects, Constants.SubLayer.NA, Constants.Outcome.Detriment);
        staticText = "Each opponent's maximum hand size is reduced by seven";
    }

    JinGitaxiasCoreAugurEffect(final JinGitaxiasCoreAugurEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID id : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(id);
            if (player != null) {
                player.setMaxHandSize(player.getMaxHandSize() - 7);
            }
        }
        return true;
    }

    @Override
    public JinGitaxiasCoreAugurEffect copy() {
        return new JinGitaxiasCoreAugurEffect(this);
    }
}