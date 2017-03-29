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
package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class GreatOakGuardian extends CardImpl {

    public GreatOakGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add("Treefolk");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Great Oak Guardian enters the battlefield, creatures target player controls get +2/+2 until end of turn. Untap them.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GreatOakGuardianEffect(), false);
        ability.addEffect(new GreatOakGuardianUntapEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public GreatOakGuardian(final GreatOakGuardian card) {
        super(card);
    }

    @Override
    public GreatOakGuardian copy() {
        return new GreatOakGuardian(this);
    }
}

class GreatOakGuardianEffect extends ContinuousEffectImpl {

    public GreatOakGuardianEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "creatures target player controls get +2/+2 until end of turn. Untap them";
    }

    public GreatOakGuardianEffect(final GreatOakGuardianEffect effect) {
        super(effect);
    }

    @Override
    public GreatOakGuardianEffect copy() {
        return new GreatOakGuardianEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(2);
                permanent.addToughness(2);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

class GreatOakGuardianUntapEffect extends OneShotEffect {

    public GreatOakGuardianUntapEffect() {
        super(Outcome.Benefit);
        this.staticText = "untap them";
    }

    public GreatOakGuardianUntapEffect(final GreatOakGuardianUntapEffect effect) {
        super(effect);
    }

    @Override
    public GreatOakGuardianUntapEffect copy() {
        return new GreatOakGuardianUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), targetPlayer.getId(), game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }
}
