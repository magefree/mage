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
package mage.sets.ravnika;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class CloudstoneCurio extends CardImpl<CloudstoneCurio> {

    private static final FilterPermanent filter = new FilterPermanent("a nonartifact permanent");
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public CloudstoneCurio(UUID ownerId) {
        super(ownerId, 257, "Cloudstone Curio", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "RAV";

        // Whenever a nonartifact permanent enters the battlefield under your control, you may return another permanent you control that shares a card type with it to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new CloudstoneCurioEffect(), filter, true, "", true));


    }

    public CloudstoneCurio(final CloudstoneCurio card) {
        super(card);
    }

    @Override
    public CloudstoneCurio copy() {
        return new CloudstoneCurio(this);
    }
}

class CloudstoneCurioEffect extends OneShotEffect<CloudstoneCurioEffect> {

    public CloudstoneCurioEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may return another permanent you control that shares a card type with it to its owner's hand";
    }

    public CloudstoneCurioEffect(final CloudstoneCurioEffect effect) {
        super(effect);
    }

    @Override
    public CloudstoneCurioEffect copy() {
        return new CloudstoneCurioEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeringCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (triggeringCreature == null) {
            triggeringCreature = (Permanent) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (triggeringCreature != null) {
            FilterPermanent filter = new FilterPermanent("another permanent you control that shares a card type with " + triggeringCreature.getName());
            filter.add(Predicates.not(new PermanentIdPredicate(triggeringCreature.getId())));
            filter.add(new ControllerPredicate(Constants.TargetController.YOU));
            Set<CardTypePredicate> cardTypes = new HashSet<CardTypePredicate>();
            for (CardType cardType :triggeringCreature.getCardType()) {
                cardTypes.add(new CardTypePredicate(cardType));
            }
            filter.add(Predicates.or(cardTypes));
            TargetPermanent target = new TargetPermanent(1,1,filter, true);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                if (target.canChoose(controller.getId(), game) && controller.chooseTarget(outcome, target, source, game)) {
                    Permanent returningCreature = game.getPermanent(target.getFirstTarget());
                    if (returningCreature != null) {
                        if (returningCreature.moveToZone(Zone.HAND, source.getSourceId(), game, true)) {
                            game.informPlayers(new StringBuilder("Cloudstone Curio: Returning ").append(returningCreature.getName()).append(" to owner's hand").toString());
                            return true;
                        }

                    }
                }
            }

        }
        return false;
    }
}
