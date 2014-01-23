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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class LoamingShaman extends CardImpl<LoamingShaman> {

    public LoamingShaman(UUID ownerId) {
        super(ownerId, 87, "Loaming Shaman", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Centaur");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Loaming Shaman enters the battlefield, target player shuffles any number of target cards from his or her graveyard into his or her library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoamingShamanEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        ability.addTarget(new LoamingShamanTargetCardsInGraveyard(0, Integer.MAX_VALUE, new FilterCard("cards in target player's graveyard")));
        this.addAbility(ability);
    }

    public LoamingShaman(final LoamingShaman card) {
        super(card);
    }

    @Override
    public LoamingShaman copy() {
        return new LoamingShaman(this);
    }
}

class LoamingShamanEffect extends OneShotEffect<LoamingShamanEffect> {

    public LoamingShamanEffect() {
        super(Outcome.Benefit);
        this.staticText = "target player shuffles any number of target cards from his or her graveyard into his or her library";
    }

    public LoamingShamanEffect(final LoamingShamanEffect effect) {
        super(effect);
    }

    @Override
    public LoamingShamanEffect copy() {
        return new LoamingShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            for (UUID targetCard : source.getTargets().get(1).getTargets()) {
                Card card = targetPlayer.getGraveyard().get(targetCard, game);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}

class LoamingShamanTargetCardsInGraveyard extends TargetCard<LoamingShamanTargetCardsInGraveyard> {

    public LoamingShamanTargetCardsInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter);
        this.targetName = filter.getMessage();
    }

    public LoamingShamanTargetCardsInGraveyard(final LoamingShamanTargetCardsInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID targetPlayerId = source.getFirstTarget();
        UUID firstTarget = this.getFirstTarget();
        Card targetCard = game.getCard(id);
        if (firstTarget != null) {
            Card card = game.getCard(firstTarget);
            if (card == null || targetCard == null
                    || !card.getOwnerId().equals(targetCard.getOwnerId())) {
                return false;
            }
        } else {
            if (targetCard == null || !targetCard.getOwnerId().equals(targetPlayerId)) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }


    @Override
    public LoamingShamanTargetCardsInGraveyard copy() {
        return new LoamingShamanTargetCardsInGraveyard(this);
    }
}
