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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class TidehollowSculler extends CardImpl {

    public TidehollowSculler(UUID ownerId) {
        super(ownerId, 202, "Tidehollow Sculler", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{B}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Zombie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tidehollow Sculler enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TidehollowScullerExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new TidehollowScullerLeaveEffect(), false));
    }

    public TidehollowSculler(final TidehollowSculler card) {
        super(card);
    }

    @Override
    public TidehollowSculler copy() {
        return new TidehollowSculler(this);
    }
}

class TidehollowScullerExileEffect extends OneShotEffect {

    public TidehollowScullerExileEffect() {
        super(Outcome.Exile);
        this.staticText = "target opponent reveals his or her hand and you choose a nonland card from it. Exile that card";
    }

    public TidehollowScullerExileEffect(final TidehollowScullerExileEffect effect) {
        super(effect);
    }

    @Override
    public TidehollowScullerExileEffect copy() {
        return new TidehollowScullerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        // 6/7/2013 	If Tidehollow Sculler leaves the battlefield before its first ability has resolved,
        //              its second ability will trigger. This ability will do nothing when it resolves.
        //              Then its first ability will resolve and exile the chosen card forever.
        Permanent sourcePermanent = (Permanent) source.getSourceObject(game);
        if (controller != null && opponent != null && sourcePermanent != null) {
            opponent.revealCards(sourcePermanent.getName(), opponent.getHand(), game);

            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                Card card = opponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getObjectExileZoneId(game, sourcePermanent), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.HAND, true);
                }
            }

            return true;
        }
        return false;
    }

}

class TidehollowScullerLeaveEffect extends OneShotEffect {

    public TidehollowScullerLeaveEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled card to its owner's hand";
    }

    public TidehollowScullerLeaveEffect(final TidehollowScullerLeaveEffect effect) {
        super(effect);
    }

    @Override
    public TidehollowScullerLeaveEffect copy() {
        return new TidehollowScullerLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exZone != null) {
                controller.moveCards(exZone, null, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
