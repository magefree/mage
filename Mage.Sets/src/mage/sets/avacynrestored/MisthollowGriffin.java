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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class MisthollowGriffin extends CardImpl<MisthollowGriffin> {

    public MisthollowGriffin(UUID ownerId) {
        super(ownerId, 68, "Misthollow Griffin", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Griffin");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // You may cast Misthollow Griffin from exile.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.EXILED, new MisthollowGriffinPlayEffect()));
    }

    public MisthollowGriffin(final MisthollowGriffin card) {
        super(card);
    }

    @Override
    public MisthollowGriffin copy() {
        return new MisthollowGriffin(this);
    }
}

class MisthollowGriffinPlayEffect extends AsThoughEffectImpl<MisthollowGriffinPlayEffect> {

    public MisthollowGriffinPlayEffect() {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfGame, Constants.Outcome.Benefit);
        staticText = "You may cast Misthollow Griffin from exile";
    }

    public MisthollowGriffinPlayEffect(final MisthollowGriffinPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MisthollowGriffinPlayEffect copy() {
        return new MisthollowGriffinPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
            if (sourceId.equals(source.getSourceId())) {
                Card card = game.getCard(source.getSourceId());
                if (card != null && card.getOwnerId().equals(source.getControllerId()) && game.getState().getZone(source.getSourceId()) == Constants.Zone.EXILED) {
                    return true;
                }
            }
            return false;
    }
}