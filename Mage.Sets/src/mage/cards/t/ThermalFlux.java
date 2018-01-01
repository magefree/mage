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
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801 & L_J
 */
public class ThermalFlux extends CardImpl {

    private static final FilterPermanent filterNonsnow = new FilterPermanent("nonsnow permanent");
    private static final FilterPermanent filterSnow = new FilterPermanent("snow permanent");

    static {
        filterNonsnow.add(Predicates.not(new SupertypePredicate(SuperType.SNOW)));
        filterSnow.add(new SupertypePredicate(SuperType.SNOW));
    }

    public ThermalFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one - 
        // Target nonsnow permanent becomes snow until end of turn.
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addTarget(new TargetPermanent(filterNonsnow));
        this.getSpellAbility().addEffect(new ThermalFluxEffect(true));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        // Target snow permanent isn't snow until end of turn.
        // Draw a card at the beginning of the next turn's upkeep.
        Mode mode = new Mode();
        mode.getTargets().add(new TargetPermanent(filterSnow));
        mode.getEffects().add(new ThermalFluxEffect(false));
        mode.getEffects().add(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        this.getSpellAbility().addMode(mode);

    }

    public ThermalFlux(final ThermalFlux card) {
        super(card);
    }

    @Override
    public ThermalFlux copy() {
        return new ThermalFlux(this);
    }
}

class ThermalFluxEffect extends ContinuousEffectImpl {

    private final boolean addSnow;

    public ThermalFluxEffect(boolean addSnow) {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.addSnow = addSnow;
        this.staticText = "Target " + (addSnow ? "non" : "") + "snow permanent " + (addSnow ? "becomes" : "isn't") + " snow until end of turn";
    }

    public ThermalFluxEffect(final ThermalFluxEffect effect) {
        super(effect);
        this.addSnow = effect.addSnow;
    }

    @Override
    public ThermalFluxEffect copy() {
        return new ThermalFluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (addSnow) {
                permanent.addSuperType(SuperType.SNOW);
            } else {
                permanent.getSuperType().remove(SuperType.SNOW);
            }
        }
        return true;
    }
}
