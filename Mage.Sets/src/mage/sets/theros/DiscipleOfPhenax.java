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
package mage.sets.theros;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class DiscipleOfPhenax extends CardImpl<DiscipleOfPhenax> {

    public DiscipleOfPhenax(UUID ownerId) {
        super(ownerId, 84, "Disciple of Phenax", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "THS";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Disciple of Phenax enters the battlefield, target player reveals a number of cards
        // from his or her hand equal to your devotion to black. You choose one of them. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscipleOfPhenaxEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);

    }

    public DiscipleOfPhenax(final DiscipleOfPhenax card) {
        super(card);
    }

    @Override
    public DiscipleOfPhenax copy() {
        return new DiscipleOfPhenax(this);
    }
}

class DiscipleOfPhenaxEffect extends OneShotEffect<DiscipleOfPhenaxEffect> {

    public DiscipleOfPhenaxEffect() {
        super(Outcome.Discard);
        staticText = "target player reveals a number of cards from his or her hand equal to your devotion to black. You choose one of them. That player discards that card";
    }

    public DiscipleOfPhenaxEffect(final DiscipleOfPhenaxEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfPhenaxEffect copy() {
        return new DiscipleOfPhenaxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int devotion = new DevotionCount(ColoredManaSymbol.B).calculate(game, source);
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (devotion > 0 && targetPlayer != null) {
            Cards revealedCards = new CardsImpl(Zone.PICK);
            int amount = Math.min(targetPlayer.getHand().size(), devotion);
            if (targetPlayer.getHand().size() > amount) {
                FilterCard filter = new FilterCard("card in target player's hand");
                TargetCardInHand chosenCards = new TargetCardInHand(amount, amount, filter);
                chosenCards.setRequired(true);
                chosenCards.setNotTarget(true);
                if (chosenCards.canChoose(targetPlayer.getId(), game) && targetPlayer.choose(Outcome.Discard, targetPlayer.getHand(), chosenCards, game)) {
                    if (!chosenCards.getTargets().isEmpty()) {
                        List<UUID> targets = chosenCards.getTargets();
                        for (UUID targetid : targets) {
                            Card card = game.getCard(targetid);
                            if (card != null) {
                                revealedCards.add(card);
                            }
                        }
                    }
                }
            } else {
                revealedCards.addAll(targetPlayer.getHand());
            }
            if (!revealedCards.isEmpty()) {
                targetPlayer.revealCards("Disciple of Phenax", revealedCards, game);
                Player you = game.getPlayer(source.getControllerId());
                if (you != null) {
                    TargetCard yourChoice = new TargetCard(Zone.PICK, new FilterCard());
                    yourChoice.setRequired(true);
                    yourChoice.setNotTarget(true);
                    if (you.choose(Outcome.Benefit, revealedCards, yourChoice, game)) {
                        Card card = targetPlayer.getHand().get(yourChoice.getFirstTarget(), game);
                        if (card != null) {
                            return targetPlayer.discard(card, source, game);
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;

        }

        return false;
    }
}
