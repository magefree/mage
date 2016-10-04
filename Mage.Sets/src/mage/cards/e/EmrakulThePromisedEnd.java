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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class EmrakulThePromisedEnd extends CardImpl {

    private static final FilterCard filter = new FilterCard("instants");
    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public EmrakulThePromisedEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{13}");
        this.supertype.add("Legendary");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EmrakulThePromisedEndCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        ability = new CastSourceTriggeredAbility(new EmrakulThePromisedEndGainControlEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from instants
        this.addAbility(new ProtectionAbility(filter));
    }

    public EmrakulThePromisedEnd(final EmrakulThePromisedEnd card) {
        super(card);
    }

    @Override
    public EmrakulThePromisedEnd copy() {
        return new EmrakulThePromisedEnd(this);
    }
}

class EmrakulThePromisedEndCostReductionEffect extends CostModificationEffectImpl {

    EmrakulThePromisedEndCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each card type among cards in your graveyard";
    }

    EmrakulThePromisedEndCostReductionEffect(EmrakulThePromisedEndCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<CardType> foundCardTypes = new HashSet<>(8);
            for (Card card : controller.getGraveyard().getCards(game)) {
                foundCardTypes.addAll(card.getCardType());
            }
            CardUtil.reduceCost(abilityToModify, foundCardTypes.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public EmrakulThePromisedEndCostReductionEffect copy() {
        return new EmrakulThePromisedEndCostReductionEffect(this);
    }
}

class EmrakulThePromisedEndGainControlEffect extends ControlTargetPlayerNextTurnEffect {

    EmrakulThePromisedEndGainControlEffect() {
        super();
        this.staticText = "you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn";
    }

    EmrakulThePromisedEndGainControlEffect(final EmrakulThePromisedEndGainControlEffect effect) {
        super(effect);
    }

    @Override
    public EmrakulThePromisedEndGainControlEffect copy() {
        return new EmrakulThePromisedEndGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            game.getState().getTurnMods().add(new TurnMod(targetPlayer.getId(), false));
        }
        return super.apply(game, source);
    }
}
