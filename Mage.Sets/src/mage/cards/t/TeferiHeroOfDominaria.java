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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.emblems.TeferiHeroOfDominariaEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class TeferiHeroOfDominaria extends CardImpl {

    public TeferiHeroOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Draw a card. At the beginning of the next end step, untap two lands.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new UntapLandsEffect(2, false));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(delayedAbility));
        this.addAbility(ability);

        // −3: Put target nonland permanent into its owner's library third from the top.
        ability = new LoyaltyAbility(new TeferiHeroOfDominariaSecondEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // −8: You get an emblem with "Whenever you draw a card, exile target permanent an opponent controls."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiHeroOfDominariaEmblem()), -8));
    }

    public TeferiHeroOfDominaria(final TeferiHeroOfDominaria card) {
        super(card);
    }

    @Override
    public TeferiHeroOfDominaria copy() {
        return new TeferiHeroOfDominaria(this);
    }
}

class TeferiHeroOfDominariaSecondEffect extends OneShotEffect {

    public TeferiHeroOfDominariaSecondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target nonland permanent into its owner's library third from the top";
    }

    public TeferiHeroOfDominariaSecondEffect(final TeferiHeroOfDominariaSecondEffect effect) {
        super(effect);
    }

    @Override
    public TeferiHeroOfDominariaSecondEffect copy() {
        return new TeferiHeroOfDominariaSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                Player owner = game.getPlayer(permanent.getOwnerId());
                if (owner == null) {
                    return false;
                }
                permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                Card card = owner.getLibrary().remove(permanent.getId(), game);
                if (card != null) {
                    owner.getLibrary().putCardThirdFromTheTop(card, game);
                    game.informPlayers(card.getLogName() + " is put into " + owner.getLogName() + "'s library third from the top");
                }
            }
            return true;
        }
        return false;
    }
}
