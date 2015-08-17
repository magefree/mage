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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class MagusOfTheScroll extends CardImpl {

    public MagusOfTheScroll(UUID ownerId) {
        super(ownerId, 169, "Magus of the Scroll", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}, {tap}: Name a card. Reveal a card at random from your hand. If it's the named card, Magus of the Scroll deals 2 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NameACardEffect(NameACardEffect.TypeOfName.ALL), new ManaCostsImpl("{3}"));
        ability.addEffect(new MagusOfTheScrollEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public MagusOfTheScroll(final MagusOfTheScroll card) {
        super(card);
    }

    @Override
    public MagusOfTheScroll copy() {
        return new MagusOfTheScroll(this);
    }
}

class MagusOfTheScrollEffect extends OneShotEffect {

    public MagusOfTheScrollEffect() {
        super(Outcome.Neutral);
        staticText = "Reveal a card at random from your hand. If it's the named card, {this} deals 2 damage to target creature or player";
    }

    public MagusOfTheScrollEffect(final MagusOfTheScrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (sourceObject != null && you != null && cardName != null && !cardName.isEmpty()) {
            if (you.getHand().size() > 0) {
                Cards revealed = new CardsImpl();
                Card card = you.getHand().getRandom(game);
                revealed.add(card);
                you.revealCards(sourceObject.getName(), revealed, game);
                if (card.getName().equals(cardName)) {
                    Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (creature != null) {
                        creature.damage(2, source.getSourceId(), game, false, true);
                        return true;
                    }
                    Player player = game.getPlayer(targetPointer.getFirst(game, source));
                    if (player != null) {
                        player.damage(2, source.getSourceId(), game, false, true);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MagusOfTheScrollEffect copy() {
        return new MagusOfTheScrollEffect(this);
    }
}