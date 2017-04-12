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

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsChoicePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class VolcanicOffering extends CardImpl {

    private static final FilterLandPermanent filterLand = new FilterLandPermanent("nonbasic land you don't control");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature you don't control");
    static {
        filterLand.add(new ControllerPredicate(TargetController.NOT_YOU));
        filterLand.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        filterCreature.add(new ControllerPredicate(TargetController.NOT_YOU));
    }
    public VolcanicOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");


        // Destroy target nonbasic land you don't control and target nonbasic land of an opponent's choice you don't control.
        // Volcanic Offering deals 7 damage to target creature you don't control and 7 damage to target creature of an opponent's choice you don't control.
        this.getSpellAbility().addEffect(new VolcanicOfferingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filterLand));
        this.getSpellAbility().addTarget(new TargetPermanent(filterCreature));

    }

    public VolcanicOffering(final VolcanicOffering card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null && (ability instanceof SpellAbility)) {
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(filterLand));
            FilterLandPermanent filterLandForOpponent = new FilterLandPermanent("nonbasic land not controlled by " + controller.getLogName());
            filterLandForOpponent.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
            filterLandForOpponent.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
            ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, filterLandForOpponent, false, true));

            ability.addTarget(new TargetPermanent(filterCreature));
            FilterCreaturePermanent filterCreatureForOpponent = new FilterCreaturePermanent("creature not controlled by " + controller.getLogName());
            filterCreatureForOpponent.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
            ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, filterCreatureForOpponent, false, true));
        }

    }

    @Override
    public VolcanicOffering copy() {
        return new VolcanicOffering(this);
    }
}

class VolcanicOfferingEffect extends OneShotEffect {

    public VolcanicOfferingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target nonbasic land you don't control and target nonbasic land of an opponent's choice you don't control.<br>" +
                "{this} deals 7 damage to target creature you don't control and 7 damage to target creature of an opponent's choice you don't control";
    }

    public VolcanicOfferingEffect(final VolcanicOfferingEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicOfferingEffect copy() {
        return new VolcanicOfferingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, false);
            }
            permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, false);
            }
            permanent = game.getPermanent(source.getTargets().get(2).getFirstTarget());
            if (permanent != null) {
                permanent.damage(7, source.getSourceId(), game, false, true);
            }
            permanent = game.getPermanent(source.getTargets().get(3).getFirstTarget());
            if (permanent != null) {
                permanent.damage(7, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
