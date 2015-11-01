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
package mage.sets.invasion;

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
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class PhyrexianDelver extends CardImpl {

    public PhyrexianDelver(UUID ownerId) {
        super(ownerId, 115, "Phyrexian Delver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "INV";
        this.subtype.add("Zombie");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Phyrexian Delver enters the battlefield, return target creature card from your graveyard to the battlefield. You lose life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhyrexianDelverEffect(), false);
        Target target = new TargetCardInYourGraveyard(1, new FilterCreatureCard("creature card from your graveyard"));
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public PhyrexianDelver(final PhyrexianDelver card) {
        super(card);
    }

    @Override
    public PhyrexianDelver copy() {
        return new PhyrexianDelver(this);
    }
}

class PhyrexianDelverEffect extends OneShotEffect {

    public PhyrexianDelverEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield. You lose life equal to that card's converted mana cost";
    }

    public PhyrexianDelverEffect(final PhyrexianDelverEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianDelverEffect copy() {
        return new PhyrexianDelverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()).equals(Zone.GRAVEYARD)) {
                result = controller.moveCards(creatureCard, Zone.BATTLEFIELD, source, game);;
            }
            controller.loseLife(creatureCard.getManaCost().convertedManaCost(), game);
            return result;
        }
        return false;
    }
}
