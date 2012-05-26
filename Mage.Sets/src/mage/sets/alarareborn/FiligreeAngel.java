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

package mage.sets.alarareborn;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class FiligreeAngel extends CardImpl<FiligreeAngel> {

    public FiligreeAngel (UUID ownerId) {
        super(ownerId, 6, "Filigree Angel", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{W}{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FiligreeAngelEffect()));
    }

    public FiligreeAngel (final FiligreeAngel card) {
        super(card);
    }

    @Override
    public FiligreeAngel copy() {
        return new FiligreeAngel(this);
    }
}

class FiligreeAngelEffect extends OneShotEffect<FiligreeAngelEffect> {
    private static FilterPermanent filter = new FilterPermanent();

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.setScopeCardType(Filter.ComparisonScope.All);
    }

    public FiligreeAngelEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "you gain 3 life for each artifact you control";
    }

    public FiligreeAngelEffect(final FiligreeAngelEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
		if (player != null) {
            int life = game.getBattlefield().count(filter, source.getControllerId(), game) * 3;
			player.gainLife(life, game);
		}
		return true;
    }

    @Override
    public FiligreeAngelEffect copy() {
        return new FiligreeAngelEffect(this);
    }

}
