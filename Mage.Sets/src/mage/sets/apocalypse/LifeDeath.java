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

package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BecomesCreatureAllEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.cards.SplitCard;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */

public class LifeDeath extends SplitCard<LifeDeath> {

    public LifeDeath(UUID ownerId) {
        super(ownerId, 130, "Life", "Death", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{G}", "{1}{B}", false);
        this.expansionSetCode = "APC";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // Life
        // All lands you control become 1/1 creatures until end of turn. They're still lands.
        getLeftHalfCard().getColor().setGreen(true);
        getLeftHalfCard().getSpellAbility().addEffect(new BecomesCreatureAllEffect(new LifeLandToken(), "lands", 
                        new FilterControlledLandPermanent("lands you control"), Duration.EndOfTurn));

        // Death
        // Return target creature card from your graveyard to the battlefield. You lose life equal to its converted mana cost.
        getRightHalfCard().getColor().setBlack(true);
        Target target = new TargetCardInYourGraveyard(1, new FilterCreatureCard("creature card from your graveyard"));
        target.setRequired(true);        
        getRightHalfCard().getSpellAbility().addTarget(target);
        getRightHalfCard().getSpellAbility().addEffect(new DeathEffect());

    }

    public LifeDeath(final LifeDeath card) {
        super(card);
    }

    @Override
    public LifeDeath copy() {
        return new LifeDeath(this);
    }
}

class LifeLandToken extends Token {
    public LifeLandToken() {
        super("", "1/1 creatures");
        cardType.add(CardType.CREATURE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

}

class DeathEffect extends OneShotEffect<DeathEffect> {

    public DeathEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield. You lose life equal to its converted mana cost";
    }

    public DeathEffect(final DeathEffect effect) {
        super(effect);
    }

    @Override
    public DeathEffect copy() {
        return new DeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()).equals(Zone.GRAVEYARD)) {
                result = controller.putOntoBattlefieldWithInfo(creatureCard, game, Zone.GRAVEYARD, source.getSourceId());
            }            
            controller.loseLife(creatureCard.getManaCost().convertedManaCost(), game);
            return result;
        }
        return false;
    }
}
