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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.sets.tokens.EmptyToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward
 */
public class Seance extends CardImpl<Seance> {

    public Seance(UUID ownerId) {
        super(ownerId, 20, "Seance", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // At the beginning of each upkeep, you may exile target creature card from your graveyard. If you do, put a token onto the battlefield that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SeanceEffect(), Constants.TargetController.ANY, true);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        this.addAbility(ability);
    }

    public Seance(final Seance card) {
        super(card);
    }

    @Override
    public Seance copy() {
        return new Seance(this);
    }
}

class SeanceEffect extends OneShotEffect<SeanceEffect> {

    public SeanceEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "put a token onto the battlefield that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step";
    }

    public SeanceEffect(final SeanceEffect effect) {
        super(effect);
    }

    @Override
    public SeanceEffect copy() {
        return new SeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            card.moveToExile(null, "", source.getSourceId(), game);
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card);

            if (!token.getSubtype().contains("Spirit"))
                token.getSubtype().add("Spirit");
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);

            return true;
        }

        return false;
    }

}
