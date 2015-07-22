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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class JolraelEmpressOfBeasts extends CardImpl {

    public JolraelEmpressOfBeasts(UUID ownerId) {
        super(ownerId, 115, "Jolrael, Empress of Beasts", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "PCY";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Spellshaper");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}, {tap}, Discard two cards: All lands target player controls become 3/3 creatures until end of turn. They're still lands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JolraelEmpressOfBeastsEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards"))));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public JolraelEmpressOfBeasts(final JolraelEmpressOfBeasts card) {
        super(card);
    }

    @Override
    public JolraelEmpressOfBeasts copy() {
        return new JolraelEmpressOfBeasts(this);
    }
}

class JolraelEmpressOfBeastsEffect extends OneShotEffect {

    public JolraelEmpressOfBeastsEffect() {
        super(Outcome.Benefit);
        this.staticText = "All lands target player controls become 3/3 creatures until end of turn. They're still lands.";
    }

    public JolraelEmpressOfBeastsEffect(final JolraelEmpressOfBeastsEffect effect) {
        super(effect);
    }

    @Override
    public JolraelEmpressOfBeastsEffect copy() {
        return new JolraelEmpressOfBeastsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new PlayerIdPredicate(targetPlayer.getId()));
            game.addEffect(new BecomesCreatureAllEffect(new JolraelLandsToken(), "lands", filter, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}

class JolraelLandsToken extends Token {

    public JolraelLandsToken() {
        super("", "3/3 creature");
        cardType.add(CardType.CREATURE);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
}
