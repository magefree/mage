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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class PostmortemLunge extends CardImpl<PostmortemLunge> {

    public PostmortemLunge(UUID ownerId) {
        super(ownerId, 70, "Postmortem Lunge", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{BP}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        this.getSpellAbility().addEffect(new PostmortemLungeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
    }

    public PostmortemLunge(final PostmortemLunge card) {
        super(card);
    }

    @Override
    public PostmortemLunge copy() {
        return new PostmortemLunge(this);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Card card = game.getCard(ability.getFirstTarget());
        if (card != null) {
            // insert at the beginning (so it will be {2}{B}, not {B}{2})
            ability.getManaCostsToPay().add(0, new GenericManaCost(card.getManaCost().convertedManaCost()));
        }
        // no {X} anymore as we already have chosen the target with defined manacost
        for (ManaCost cost : ability.getManaCostsToPay()) {
            if (cost instanceof VariableCost) {
                cost.setPaid();
            }
        }
    }
}

class PostmortemLungeEffect extends OneShotEffect<PostmortemLungeEffect> {

    public PostmortemLungeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card with converted mana cost X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    public PostmortemLungeEffect(final PostmortemLungeEffect effect) {
        super(effect);
    }

    @Override
    public PostmortemLungeEffect copy() {
        return new PostmortemLungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player player = game.getPlayer(card.getOwnerId());
        if (card != null && player != null && player.removeFromGraveyard(card, game)) {
            card.addAbility(HasteAbility.getInstance());
            card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(card.getId()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);

            return true;
        }

        return false;
    }
}
