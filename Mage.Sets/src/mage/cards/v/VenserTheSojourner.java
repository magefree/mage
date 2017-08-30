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
package mage.cards.v;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.command.emblems.VenserTheSojournerEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public class VenserTheSojourner extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public VenserTheSojourner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Venser");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        LoyaltyAbility ability1 = new LoyaltyAbility(new VenserTheSojournerEffect(), 2);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // -1: Creatures can't be blocked this turn.
        this.addAbility(new LoyaltyAbility(new CantBeBlockedAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn), -1));

        // -8: You get an emblem with "Whenever you cast a spell, exile target permanent."
        LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new VenserTheSojournerEmblem()), -8);
        this.addAbility(ability2);
    }

    public VenserTheSojourner(final VenserTheSojourner card) {
        super(card);
    }

    @Override
    public VenserTheSojourner copy() {
        return new VenserTheSojourner(this);
    }

}

class VenserTheSojournerEffect extends OneShotEffect {

    private static final String effectText = "Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step";

    VenserTheSojournerEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    VenserTheSojournerEffect(VenserTheSojournerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                        //create delayed triggered ability
                        Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
                        effect.setText("Return it to the battlefield under your control at the beginning of the next end step");
                        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public VenserTheSojournerEffect copy() {
        return new VenserTheSojournerEffect(this);
    }

}
