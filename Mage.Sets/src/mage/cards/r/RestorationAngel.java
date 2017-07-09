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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author noxx
 *
 */
public class RestorationAngel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Angel creature you control");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.ANGEL)));
    }

    public RestorationAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add("Angel");

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control, then return that card to the battlefield under your control
        Ability ability = new EntersBattlefieldTriggeredAbility(new RestorationAngelEffect(), true);
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    public RestorationAngel(final RestorationAngel card) {
        super(card);
    }

    @Override
    public RestorationAngel copy() {
        return new RestorationAngel(this);
    }
}

class RestorationAngelEffect extends OneShotEffect {

    public RestorationAngelEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target non-Angel creature you control, then return that card to the battlefield under your control";
    }

    public RestorationAngelEffect(final RestorationAngelEffect effect) {
        super(effect);
    }

    @Override
    public RestorationAngelEffect copy() {
        return new RestorationAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null && sourcePermanent != null) {
                int zcc = permanent.getZoneChangeCounter(game);
                controller.moveCards(permanent, Zone.EXILED, source, game);
                Card card = game.getCard(permanent.getId());
                if (card != null
                        && card.getZoneChangeCounter(game) == zcc + 1
                        && game.getState().getZone(card.getId()) == Zone.EXILED) {
                    return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return false;
    }
}
