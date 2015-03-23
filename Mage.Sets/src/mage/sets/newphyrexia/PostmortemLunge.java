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
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class PostmortemLunge extends CardImpl {

    public PostmortemLunge(UUID ownerId) {
        super(ownerId, 70, "Postmortem Lunge", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{BP}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        // Return target creature card with converted mana cost X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PostmortemLungeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
    }

    public PostmortemLunge(final PostmortemLunge card) {
        super(card);
    }

    @Override
    public PostmortemLunge copy() {
        return new PostmortemLunge(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with converted mana cost " + xValue +  " or less from your graveyard");
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, xValue + 1));
        ability.getTargets().add(new TargetCardInYourGraveyard(filter));
    }
}

class PostmortemLungeEffect extends OneShotEffect {

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
        
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player == null) {
                return false;
            }
            game.getState().addOtherAbility(card, HasteAbility.getInstance());
            card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(card.getId()));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game));
            game.addDelayedTriggeredAbility(delayedAbility);

            return true;
        }

        return false;
    }
}
