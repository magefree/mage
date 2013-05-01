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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continious.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.FuseAbility;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TurnBurn extends SplitCard<TurnBurn> {

    public TurnBurn(UUID ownerId) {
        super(ownerId, 134, "Turn - Burn", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{1}{R}");
        this.expansionSetCode = "DGM";

        this.color.setBlue(true);
        this.color.setRed(true);

        // Turn
        Card leftHalfCard = this.createLeftHalfCard("Turn", "{2}{U}");
        // Target creature loses all abilities and becomes a 0/1 red Weird until end of turn.
        leftHalfCard.getSpellAbility().addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn));
        leftHalfCard.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new WeirdToken(),null, Duration.EndOfTurn));
        leftHalfCard.getSpellAbility().addTarget(new TargetCreaturePermanent(true));

        // Burn
        Card rightHalfCard = this.createRightHalfCard("Burn", "{1}{R}");
        // Burn deals 2 damage to target creature or player.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("Burn deals 2 damage to target creature or player.");
        rightHalfCard.getSpellAbility().addEffect(effect);
        rightHalfCard.getSpellAbility().addTarget(new TargetCreatureOrPlayer(true));


        // Fuse (You may cast one or both halves of this card from your hand.)
        this.addAbility(new FuseAbility(this, this.getManaCost()));
    }

    public TurnBurn(final TurnBurn card) {
        super(card);
    }

    @Override
    public TurnBurn copy() {
        return new TurnBurn(this);
    }

    private class WeirdToken extends Token {

        private WeirdToken() {
            super("Weird", "0/1 red Weird");
            cardType.add(Constants.CardType.CREATURE);
            color = ObjectColor.RED;
            subtype.add("Weird");
            power = new MageInt(0);
            toughness = new MageInt(1);
        }

    }

}
