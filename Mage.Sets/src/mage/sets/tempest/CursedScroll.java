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
package mage.sets.tempest;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.Sets;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public class CursedScroll extends CardImpl<CursedScroll> {

    public CursedScroll(UUID ownerId) {
        super(ownerId, 271, "Cursed Scroll", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "TMP";

        // {3}, {tap}: Name a card. Reveal a card at random from your hand. If it's the named card, Cursed Scroll deals 2 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CursedScrollEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CursedScroll(final CursedScroll card) {
        super(card);
    }

    @Override
    public CursedScroll copy() {
        return new CursedScroll(this);
    }
}

class CursedScrollEffect extends OneShotEffect<CursedScrollEffect> {

    public CursedScrollEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "Name a card. Reveal a card at random from your hand. If it's the named card, {this} deals 2 damage to target creature or player";
    }

    public CursedScrollEffect(final CursedScrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(Sets.getCardNames());
            cardChoice.clearChoice();
            while (!you.choose(Constants.Outcome.Damage, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("Cursed Scroll, named card: [" + cardName + "]");
            if (you.getHand().size() > 0) {
                Cards revealed = new CardsImpl();
                Card card = you.getHand().getRandom(game);
                revealed.add(card);
                you.revealCards("Cursed Scroll", revealed, game);
                if (card.getName().equals(cardName)) {
                    TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
                    if (target.canChoose(you.getId(), game)) {
                        if (you.chooseTarget(Constants.Outcome.Damage, target, source, game)) {
                            Permanent creature = game.getPermanent(target.getFirstTarget());
                            if (creature != null) {
                                creature.damage(2, source.getSourceId(), game, true, false);
                                return true;
                            }
                            Player player = game.getPlayer(target.getFirstTarget());
                            if (player != null) {
                                player.damage(2, source.getSourceId(), game, true, false);
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CursedScrollEffect copy() {
        return new CursedScrollEffect(this);
    }
}
