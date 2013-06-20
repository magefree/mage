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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class WarrenPilferers extends CardImpl<WarrenPilferers> {

    public WarrenPilferers(UUID ownerId) {
        super(ownerId, 103, "Warren Pilferers", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Warren Pilferers enters the battlefield, return target creature card from your graveyard to your hand. If that card is a Goblin card, Warren Pilferers gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WarrenPilferersReturnEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.addAbility(ability);
    }

    public WarrenPilferers(final WarrenPilferers card) {
        super(card);
    }

    @Override
    public WarrenPilferers copy() {
        return new WarrenPilferers(this);
    }
}

class WarrenPilferersReturnEffect extends OneShotEffect<WarrenPilferersReturnEffect> {

    public WarrenPilferersReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return target creature card from your graveyard to your hand. If that card is a Goblin card, Warren Pilferers gains haste until end of turn";
    }

    public WarrenPilferersReturnEffect(final WarrenPilferersReturnEffect effect) {
        super(effect);
    }

    @Override
    public WarrenPilferersReturnEffect copy() {
        return new WarrenPilferersReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            if (card.getSubtype().contains("Goblin")) {
                game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }

}
