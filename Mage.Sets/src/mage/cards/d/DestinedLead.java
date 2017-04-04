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

package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author stravant
 */


public class DestinedLead extends SplitCard {

    public DestinedLead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY},"{1}{B}","{3}{G}",false);

        // Destined
        // Target creature gets +1/+0 and gains indestructible until end of turn.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+0");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains indestructible until end of turn");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // to

        // Lead
        // All creatures able to block target creature this turn must do so.
        ((CardImpl)(getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getRightHalfCard().getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
    }

    public DestinedLead(final DestinedLead card) {
        super(card);
    }

    @Override
    public DestinedLead copy() {
        return new DestinedLead(this);
    }
}

