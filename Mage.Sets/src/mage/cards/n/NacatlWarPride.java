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
package mage.cards.n;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public class NacatlWarPride extends CardImpl {

    public NacatlWarPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        this.subtype.add("Cat");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nacatl War-Pride must be blocked by exactly one creature if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect()));

        // Whenever Nacatl War-Pride attacks, put X tokens that are copies of Nacatl War-Pride onto the battlefield tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new NacatlWarPrideEffect(), false));
    }

    public NacatlWarPride(final NacatlWarPride card) {
        super(card);
    }

    @Override
    public NacatlWarPride copy() {
        return new NacatlWarPride(this);
    }
}

class NacatlWarPrideEffect extends OneShotEffect {

    public NacatlWarPrideEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put X tokens onto the battlefield that are copies of Nacatl War-Pride onto the battlefield tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.";
    }

    public NacatlWarPrideEffect(final NacatlWarPrideEffect effect) {
        super(effect);
    }

    @Override
    public NacatlWarPrideEffect copy() {
        return new NacatlWarPrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent origNactalWarPride = game.getPermanent(source.getSourceId());
        if (origNactalWarPride == null) {
            return false;
        }

        // Count the number of creatures attacked opponent controls
        UUID defenderId = game.getCombat().getDefendingPlayerId(origNactalWarPride.getId(), game);
        int count = 0;
        if (defenderId != null) {
            count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), defenderId, game);
        }

        if (count == 0) {
            return false;
        }

        List<Permanent> copies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(origNactalWarPride);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), true, true);

            for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    copies.add(tokenPermanent);
                }
            }
        }

        if (!copies.isEmpty()) {
            FixedTargets fixedTargets = new FixedTargets(copies, game);
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(fixedTargets);
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            return true;
        }

        return false;
    }
}
