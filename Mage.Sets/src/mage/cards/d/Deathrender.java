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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Blinke
 */
public class Deathrender extends CardImpl {

    public Deathrender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        // Whenever equipped creature dies, you may put a creature card from your hand onto the battlefield and attach Deathrender to it.
        this.addAbility(new DiesAttachedTriggeredAbility(new DeathrenderEffect(), "equipped creature"));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public Deathrender(final Deathrender card) {
        super(card);
    }

    @Override
    public Deathrender copy() {
        return new Deathrender(this);
    }
}

class DeathrenderEffect extends OneShotEffect {

    DeathrenderEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card from your hand onto the battlefield and attach {this} to it";
    }

    DeathrenderEffect(final DeathrenderEffect effect) {
        super(effect);
    }

    @Override
    public DeathrenderEffect copy() {
        return new DeathrenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            FilterCard filter = new FilterCreatureCard();
            TargetCardInHand target = new TargetCardInHand(0, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                Card creatureInHand = game.getCard(target.getFirstTarget());
                if (creatureInHand != null) {
                    if (controller.moveCards(creatureInHand, Zone.BATTLEFIELD, source, game)) {
                        game.getPermanent(creatureInHand.getId()).addAttachment(sourcePermanent.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
