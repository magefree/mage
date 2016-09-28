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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public class SorinGrimNemesis extends CardImpl {

    public SorinGrimNemesis(UUID ownerId) {
        super(ownerId, 251, "Sorin, Grim Nemesis", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{B}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Sorin");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(6));

        // +1: Reveal the top card of your library and put that card into your hand. Each opponent loses life equal to its converted mana cost.
        this.addAbility(new LoyaltyAbility(new SorinGrimNemesisRevealEffect(), 1));

        // -X: Sorin, Grim Nemesis deals X damage to target creature or planeswalker and you gain X life.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(SorinXValue.getDefault()));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addEffect(new GainLifeEffect(SorinXValue.getDefault()));
        this.addAbility(ability);

        // -9: Put a number of 1/1 black Vampire Knight creature tokens with lifelink onto the battlefield equal to the highest life total among all players.
        this.addAbility(new LoyaltyAbility(new SorinTokenEffect(), -9));
    }

    public SorinGrimNemesis(final SorinGrimNemesis card) {
        super(card);
    }

    @Override
    public SorinGrimNemesis copy() {
        return new SorinGrimNemesis(this);
    }
}

class SorinGrimNemesisRevealEffect extends OneShotEffect {

    public SorinGrimNemesisRevealEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. Each opponent loses life equal to that card's converted mana cost";
    }

    public SorinGrimNemesisRevealEffect(final SorinGrimNemesisRevealEffect effect) {
        super(effect);
    }

    @Override
    public SorinGrimNemesisRevealEffect copy() {
        return new SorinGrimNemesisRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Sorin, Grim Nemesis", cards, game);

            if (card != null &&
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false)) {
                for (UUID playerId : game.getOpponents(source.getControllerId())) {
                    if (card.getConvertedManaCost() > 0) {
                        Player opponent = game.getPlayer(playerId);
                        if (opponent != null) {
                            opponent.loseLife(card.getConvertedManaCost(), game, false);
                        }
                    }
                }
            return true;
            }
        }
        return false;
    }
}

class SorinXValue implements DynamicValue {

    private static final SorinXValue defaultValue = new SorinXValue();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                return ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static SorinXValue getDefault() {
        return defaultValue;
    }
}

class SorinTokenEffect extends OneShotEffect {
   SorinTokenEffect() {
        super(Outcome.GainLife);
        staticText = "Put a number of 1/1 black Vampire Knight creature tokens with lifelink onto the battlefield equal to the highest life total among all players";
    }

   SorinTokenEffect(final SorinTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxLife = 0;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife < p.getLife()) {
                    maxLife = p.getLife();
                }
            }
        }
        new CreateTokenEffect(new VampireKnightToken(), maxLife).apply(game, source);
        return true;
    }

    @Override
    public SorinTokenEffect copy() {
        return new SorinTokenEffect(this);
    }
}

class VampireKnightToken extends Token {

    public VampireKnightToken() {
        super("Vampire Knight", "1/1 black Vampire Knight creature token with lifelink");
        cardType.add(CardType.CREATURE);
        subtype.add("Vampire");
        subtype.add("Knight");
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }
}
