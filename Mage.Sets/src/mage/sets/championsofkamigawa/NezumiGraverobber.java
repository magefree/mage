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
package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 * @author Loki
 */
public class NezumiGraverobber extends CardImpl<NezumiGraverobber> {

    public NezumiGraverobber(UUID ownerId) {
        super(ownerId, 129, "Nezumi Graverobber", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Rat");
        this.subtype.add("Rogue");
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addTarget(new TargetCardInOpponentsGraveyard(new FilterCard("card from an opponent's graveyard")));
        ability.addEffect(new NezumiGraverobberFlipEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new NighteyesTheDesecratorToken()), FlippedCondition.getInstance(), "")));
    }

    public NezumiGraverobber(final NezumiGraverobber card) {
        super(card);
    }

    @Override
    public NezumiGraverobber copy() {
        return new NezumiGraverobber(this);
    }
}

class NezumiGraverobberFlipEffect extends OneShotEffect<NezumiGraverobberFlipEffect> {

    NezumiGraverobberFlipEffect() {
        super(Constants.Outcome.BecomeCreature);
        staticText = "If no cards are in that graveyard, flip Nezumi Graverobber";
    }

    NezumiGraverobberFlipEffect(final NezumiGraverobberFlipEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(source));
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                if (player.getGraveyard().size() == 0) {
                    Permanent p = game.getPermanent(source.getSourceId());
                    if (p != null) {
                        p.flip(game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public NezumiGraverobberFlipEffect copy() {
        return new NezumiGraverobberFlipEffect(this);
    }

}

class NighteyesTheDesecratorToken extends Token {
    NighteyesTheDesecratorToken() {
        super("Nighteyes The Desecrator", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Rat");
        subtype.add("Wizard");
        power = new MageInt(4);
        toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{4}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }
}
