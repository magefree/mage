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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.MageObject;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DomriRade extends CardImpl {

    public DomriRade(UUID ownerId) {
        super(ownerId, 156, "Domri Rade", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Domri");



        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +1: Look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand.
        this.addAbility(new LoyaltyAbility(new DomriRadeEffect1(), 1));

        // -2: Target creature you control fights another target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new FightTargetsEffect(), -2);
        ability2.addTarget(new TargetControlledCreaturePermanent());
        ability2.addTarget(new DomriRadeTargetOtherCreaturePermanent());
        this.addAbility(ability2);

        // -7: You get an emblem with "Creatures you control have double strike, trample, hexproof and haste."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DomriRadeEmblem()), -7));

    }

    public DomriRade(final DomriRade card) {
        super(card);
    }

    @Override
    public DomriRade copy() {
        return new DomriRade(this);
    }
}

class DomriRadeEffect1 extends OneShotEffect {

    public DomriRadeEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand";
    }

    public DomriRadeEffect1(final DomriRadeEffect1 effect) {
        super(effect);
    }

    @Override
    public DomriRadeEffect1 copy() {
        return new DomriRadeEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null && controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards(sourceObject.getName(), cards, game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    if (controller.chooseUse(outcome, "Reveal " + card.getName() + " and put it into your hand?", source, game)) {
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                        controller.revealCards(sourceObject.getName(), cards, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class DomriRadeEmblem extends Emblem {
    // "Creatures you control have double strike, trample, hexproof and haste."
    public DomriRadeEmblem() {
        this.setName("EMBLEM: Domri Rade");
        FilterPermanent filter = new FilterControlledCreaturePermanent("Creatures");
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfGame, filter);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }
}

class DomriRadeTargetOtherCreaturePermanent extends TargetCreaturePermanent {
    
    public DomriRadeTargetOtherCreaturePermanent() {
        super();
    }

    public DomriRadeTargetOtherCreaturePermanent(final DomriRadeTargetOtherCreaturePermanent target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (source.getTargets().get(0).getTargets().contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game);
    }

    @Override
    public DomriRadeTargetOtherCreaturePermanent copy() {
        return new DomriRadeTargetOtherCreaturePermanent(this);
    }

}
