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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class EmeriaShepherd extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("nonland permanent card from your graveyard");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public EmeriaShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Landfall</i> — Whenever a land enters the battlefield under your control, you may return target nonland permanent card from your graveyard to your hand.
        // If that land is a Plains, you may return that nonland permanent card to the battlefield instead.
        Ability ability = new LandfallAbility(Zone.BATTLEFIELD, new EmeriaShepherdReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterPermanentCard(filter)));
        this.addAbility(ability);
    }

    public EmeriaShepherd(final EmeriaShepherd card) {
        super(card);
    }

    @Override
    public EmeriaShepherd copy() {
        return new EmeriaShepherd(this);
    }
}

class EmeriaShepherdReturnToHandTargetEffect extends OneShotEffect {

    public EmeriaShepherdReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = "you may return target nonland permanent card from your graveyard to your hand. If that land is a Plains, you may return that nonland permanent card to the battlefield instead";
    }

    public EmeriaShepherdReturnToHandTargetEffect(final EmeriaShepherdReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public EmeriaShepherdReturnToHandTargetEffect copy() {
        return new EmeriaShepherdReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent triggeringLand = ((LandfallAbility) source).getTriggeringPermanent();
        if (controller == null || triggeringLand == null) {
            return false;
        }
        Zone toZone = Zone.HAND;
        if (triggeringLand.getSubtype(game).contains(SubType.PLAINS)
                && controller.chooseUse(Outcome.PutCardInPlay, "Put the card to battlefield instead?", source, game)) {
            toZone = Zone.BATTLEFIELD;
        }
        return controller.moveCards(new CardsImpl(targetPointer.getTargets(game, source)), toZone, source, game);
    }

}
