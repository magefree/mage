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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class MindclawShaman extends CardImpl<MindclawShaman> {

    public MindclawShaman(UUID ownerId) {
        super(ownerId, 142, "Mindclaw Shaman", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "M13";
        this.subtype.add("Viashino");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mindclaw Shaman enters the battlefield, target opponent reveals his or her hand. You may cast an instant or sorcery card from it without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MindclawShamanEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public MindclawShaman(final MindclawShaman card) {
        super(card);
    }

    @Override
    public MindclawShaman copy() {
        return new MindclawShaman(this);
    }
}

class MindclawShamanEffect extends OneShotEffect<MindclawShamanEffect> {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");
    
    static {
        filter.add(Predicates.or(
        new CardTypePredicate(CardType.INSTANT),
        new CardTypePredicate(CardType.SORCERY)));
    }
    
    public MindclawShamanEffect() {
        super(Constants.Outcome.Discard);
        this.staticText = "target opponent reveals his or her hand. You may cast an instant or sorcery card from it without paying its mana cost";
    }

    public MindclawShamanEffect(final MindclawShamanEffect effect) {
        super(effect);
    }

    @Override
    public MindclawShamanEffect copy() {
        return new MindclawShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (targetOpponent != null) {
            if (targetOpponent.getHand().size() > 0) {
                targetOpponent.revealCards("Mindclaw Shaman", targetOpponent.getHand(), game);
                Player you = game.getPlayer(source.getControllerId());
                if (you != null) {
                    TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                    target.setRequired(true);
                    target.setNotTarget(true);
                    if (you.choose(Constants.Outcome.Benefit, targetOpponent.getHand(), target, game)) {
                        Card chosenCard = targetOpponent.getHand().get(target.getFirstTarget(), game);
                        if (chosenCard != null) {
                            if (targetOpponent != null) {
                                if (you.chooseUse(Constants.Outcome.Benefit, "Cast the chosen card?", game)) {
                                    you.cast(chosenCard.getSpellAbility(), game, true);
                                } else {
                                        game.informPlayers("Mindclaw Shaman: " + you.getName() + " canceled casting the card.");
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}