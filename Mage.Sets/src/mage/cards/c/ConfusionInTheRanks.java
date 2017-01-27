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
package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author anonymous
 */
public class ConfusionInTheRanks extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    private final UUID originalId;

    public ConfusionInTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Whenever an artifact, creature, or enchantment enters the battlefield, its controller chooses target permanent another player controls that shares a card type with it. Exchange control of those permanents.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(Duration.EndOfGame, "its controller chooses target permanent another player controls that shares a card type with it. Exchange control of those permanents"),
                filter,
                false,
                SetTargetPointer.PERMANENT,
                null);
        ability.addTarget(new TargetPermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public ConfusionInTheRanks(final ConfusionInTheRanks card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            UUID enteringPermanentId = null;
            for (Effect effect : ability.getEffects()) {
                enteringPermanentId = effect.getTargetPointer().getFirst(game, ability);
            }
            if (enteringPermanentId != null) {
                Permanent enteringPermanent = game.getPermanent(enteringPermanentId);
                if (enteringPermanent != null) {
                    ability.setControllerId(enteringPermanent.getControllerId());
                    ability.getTargets().clear();
                    FilterPermanent filterTarget = new FilterPermanent();
                    String message = "";
                    filterTarget.add(Predicates.not(new ControllerIdPredicate(enteringPermanent.getControllerId())));
                    Set<CardTypePredicate> cardTypesPredicates = new HashSet<>(1);
                    for (CardType cardTypeEntering : enteringPermanent.getCardType()) {
                        cardTypesPredicates.add(new CardTypePredicate(cardTypeEntering));
                        if (!message.isEmpty()) {
                            message += "or ";
                        }
                        message += cardTypeEntering.toString().toLowerCase() + ' ';
                    }
                    filterTarget.add(Predicates.or(cardTypesPredicates));
                    message += "you do not control";
                    filterTarget.setMessage(message);
                    ability.getTargets().add(new TargetPermanent(filterTarget));
                }
            }
        }
    }

    @Override
    public ConfusionInTheRanks copy() {
        return new ConfusionInTheRanks(this);
    }
}
