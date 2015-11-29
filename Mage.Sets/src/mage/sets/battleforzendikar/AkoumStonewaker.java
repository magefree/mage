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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AkoumStonewaker extends CardImpl {

    public AkoumStonewaker(UUID ownerId) {
        super(ownerId, 140, "Akoum Stonewaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Landfall</i> — Whenever a land enters the battlefield under your control, you may pay {2}{R}. If you do, put a 3/1 red Elemental creature token with trample and haste onto the battlefield.
        // Exile that token at the beginning of the next end step.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(new AkoumStonewakerEffect(), new ManaCostsImpl("{2}{R}")), false));

    }

    public AkoumStonewaker(final AkoumStonewaker card) {
        super(card);
    }

    @Override
    public AkoumStonewaker copy() {
        return new AkoumStonewaker(this);
    }
}

class AkoumStonewakerEffect extends OneShotEffect {

    public AkoumStonewakerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a 3/1 red Elemental creature token with trample and haste onto the battlefield. Exile that token at the beginning of the next end step";
    }

    public AkoumStonewakerEffect(final AkoumStonewakerEffect effect) {
        super(effect);
    }

    @Override
    public AkoumStonewakerEffect copy() {
        return new AkoumStonewakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new AkoumStonewakerElementalToken();
        if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                }
            }
            return true;
        }
        return false;
    }
}

class AkoumStonewakerElementalToken extends Token {

    public AkoumStonewakerElementalToken() {
        super("Elemental", "3/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Elemental");
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.setOriginalExpansionSetCode("BFZ");
        this.setTokenType(1);
    }
}
