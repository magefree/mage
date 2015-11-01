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
package mage.sets.jacevsvraska;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class BodyDouble extends CardImpl {

    public BodyDouble(UUID ownerId) {
        super(ownerId, 15, "Body Double", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "DDM";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Body Double enter the battlefield as a copy of any creature card in a graveyard.
        this.addAbility(new EntersBattlefieldAbility(new BodyDoubleCopyEffect(), true));

    }

    public BodyDouble(final BodyDouble card) {
        super(card);
    }

    @Override
    public BodyDouble copy() {
        return new BodyDouble(this);
    }
}

class BodyDoubleCopyEffect extends OneShotEffect {

    public BodyDoubleCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "as a copy of any creature card in a graveyard";
    }

    public BodyDoubleCopyEffect(final BodyDoubleCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
            target.setNotTarget(true);
            if (target.canChoose(source.getControllerId(), game)) {
                player.choose(outcome, target, source.getSourceId(), game);
                Card copyFromCard = game.getCard(target.getFirstTarget());
                if (copyFromCard != null) {
                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
                    game.addEffect(copyEffect, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BodyDoubleCopyEffect copy() {
        return new BodyDoubleCopyEffect(this);
    }
}
