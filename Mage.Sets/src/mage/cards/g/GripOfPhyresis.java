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

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GermToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class GripOfPhyresis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate("Equipment"));
    }

    public GripOfPhyresis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Gain control of target Equipment, then create a 0/0 black Germ creature token and attach that Equipment to it.
        GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame, true);
        effect.setText("Gain control of target equipment");
        this.getSpellAbility().addEffect(effect);
        Target targetEquipment = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(targetEquipment);
        this.getSpellAbility().addEffect(new GripOfPhyresisEffect());
    }

    public GripOfPhyresis(final GripOfPhyresis card) {
        super(card);
    }

    @Override
    public GripOfPhyresis copy() {
        return new GripOfPhyresis(this);
    }
}

class GripOfPhyresisEffect extends CreateTokenEffect {

    GripOfPhyresisEffect() {
        super(new GermToken());
    }

    GripOfPhyresisEffect(final GripOfPhyresisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanent(source.getFirstTarget());

        if (controller != null && equipment != null) {
            if (super.apply(game, source)) {
                Permanent germ = game.getPermanent(this.getLastAddedTokenId());
                if (germ != null) {
                    germ.addAttachment(equipment.getId(), game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GripOfPhyresisEffect copy() {
        return new GripOfPhyresisEffect(this);
    }
}
