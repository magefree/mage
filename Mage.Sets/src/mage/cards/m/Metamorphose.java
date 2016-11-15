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
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public class Metamorphose extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public Metamorphose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Put target permanent an opponent controls on top of its owner's library. That opponent may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new MetamorphoseEffect());

    }

    public Metamorphose(final Metamorphose card) {
        super(card);
    }

    @Override
    public Metamorphose copy() {
        return new Metamorphose(this);
    }
}

class MetamorphoseEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard("artifact, creature, enchantment, or land card");

    static{
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)
        ));
    }

    public MetamorphoseEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "That opponent may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield.";
    }

    public MetamorphoseEffect (final MetamorphoseEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller.chooseUse(Outcome.PutCardInPlay, "Do you wish to put an artifact, creature, enchantment, or land card onto the battlefield?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                target.clearChosen();
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public MetamorphoseEffect copy() { return new MetamorphoseEffect(this); }
}