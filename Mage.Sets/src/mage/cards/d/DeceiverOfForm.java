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

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DeceiverOfForm extends CardImpl {

    public DeceiverOfForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{C}");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // At the beginning of combat on your turn, reveal the top card of your library.
        // If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn.
        // You may put that card on the bottom of your library.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DeceiverOfFormEffect(), TargetController.YOU, false));
    }

    public DeceiverOfForm(final DeceiverOfForm card) {
        super(card);
    }

    @Override
    public DeceiverOfForm copy() {
        return new DeceiverOfForm(this);
    }
}

class DeceiverOfFormEffect extends OneShotEffect {

    public DeceiverOfFormEffect() {
        super(Outcome.Copy);
        this.staticText = "reveal the top card of your library. If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn. You may put that card on the bottom of your library";
    }

    public DeceiverOfFormEffect(final DeceiverOfFormEffect effect) {
        super(effect);
    }

    @Override
    public DeceiverOfFormEffect copy() {
        return new DeceiverOfFormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            Card copyFromCard = controller.getLibrary().getFromTop(game);
            if (copyFromCard != null) {
                Cards cards = new CardsImpl(copyFromCard);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (copyFromCard.isCreature()) {
                    if (controller.chooseUse(outcome, "Let creatures you control other than "
                            + sourceObject.getLogName() + " becomes copies of " + copyFromCard.getLogName() + " until end of turn?", source, game)) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controller.getId(), game)) {
                            if (!permanent.getId().equals(sourceObject.getId())) {
                                Permanent newBluePrint = null;
                                newBluePrint = new PermanentCard((Card) copyFromCard, source.getControllerId(), game);
                                newBluePrint.assignNewId();
                                CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, permanent.getId());
                                copyEffect.newId();
                                Ability newAbility = source.copy();
                                copyEffect.init(newAbility, game);
                                game.addEffect(copyEffect, newAbility);
                            }
                        }
                    }
                }
                if (controller.chooseUse(outcome, "Move " + copyFromCard.getLogName() + " to the bottom of your library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(copyFromCard, source.getSourceId(), game, Zone.LIBRARY, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
