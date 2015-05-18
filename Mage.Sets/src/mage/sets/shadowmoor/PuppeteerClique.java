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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public class PuppeteerClique extends CardImpl {

    public PuppeteerClique(UUID ownerId) {
        super(ownerId, 75, "Puppeteer Clique", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Puppeteer Clique enters the battlefield, put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. At the beginning of your next end step, exile it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PuppeteerCliqueEffect(), false);
        Target target = new TargetCardInOpponentsGraveyard(new FilterCreatureCard("creature card from your opponent's graveyard"));
        ability.addTarget(target);
        this.addAbility(ability);

        // Persist
        this.addAbility(new PersistAbility());
    }

    public PuppeteerClique(final PuppeteerClique card) {
        super(card);
    }

    @Override
    public PuppeteerClique copy() {
        return new PuppeteerClique(this);
    }
}

class PuppeteerCliqueEffect extends OneShotEffect {

    public PuppeteerCliqueEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. At the beginning of your next end step, exile it";
    }

    public PuppeteerCliqueEffect(final PuppeteerCliqueEffect effect) {
        super(effect);
    }

    @Override
    public PuppeteerCliqueEffect copy() {
        return new PuppeteerCliqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                if (you.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId())) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        hasteEffect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(hasteEffect, source);
                        ExileTargetEffect exileEffect = new ExileTargetEffect("exile " + permanent.getLogName());
                        exileEffect.setTargetPointer(new FixedTarget(card.getId()));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect, TargetController.YOU);
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        delayedAbility.setSourceObject(source.getSourceObject(game), game);
                        game.addDelayedTriggeredAbility(delayedAbility);
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
