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
package mage.cards.y;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.command.emblems.YodaEmblem;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class YodaJediMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent you own");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public YodaJediMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{U}");
        this.subtype.add(SubType.YODA);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Look at the top two cards of your library. Put one on the bottom of your library.
        Effect effect = new LookLibraryAndPickControllerEffect(new StaticValue(2), false, new StaticValue(1),
                new FilterCard(), Zone.LIBRARY, true, false, false, Zone.LIBRARY, false, false);
        effect.setText("Look at the top two cards of your library. Put one on the bottom of your library");
        this.addAbility(new LoyaltyAbility(effect, 1));

        //  0: Exile another target permanent you own. Return that card to the battlefield under your control at the beggining of your next end step.
        Ability ability = new LoyaltyAbility(new YodaJediMasterEffect(), 0);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -5: You get an emblem with "Hexproof, you and your creatures have."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new YodaEmblem()), -5));

    }

    public YodaJediMaster(final YodaJediMaster card) {
        super(card);
    }

    @Override
    public YodaJediMaster copy() {
        return new YodaJediMaster(this);
    }
}

class YodaJediMasterEffect extends OneShotEffect {

    public YodaJediMasterEffect() {
        super(Outcome.Detriment);
        staticText = "Exile another target permanent you own. Return that card to the battlefield under your control at the beginning of your next end step";
    }

    public YodaJediMasterEffect(final YodaJediMasterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null && sourcePermanent != null) {
            if (permanent.moveToExile(source.getSourceId(), sourcePermanent.getName(), source.getSourceId(), game)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
                effect.setText("Return that card to the battlefield under your control at the beginning of your next end step");
                effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public YodaJediMasterEffect copy() {
        return new YodaJediMasterEffect(this);
    }
}
