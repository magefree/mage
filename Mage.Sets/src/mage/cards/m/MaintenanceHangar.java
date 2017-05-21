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

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class MaintenanceHangar extends CardImpl {

    private static final FilterCreaturePermanent filterPermanent = new FilterCreaturePermanent("Starship creatures");

    static {
        filterPermanent.add(new SubtypePredicate(SubType.STARSHIP));
    }

    public MaintenanceHangar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // At the beginning of your upkeep, remove an additional repair counter from each card in your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterMaintenanceHangarEffect(), TargetController.YOU, false));

        // Starship creatures you control and starship creatures in your graveyard have Repair 6.
        Effect effect = new GainAbilityControlledEffect(new RepairAbility(6), Duration.WhileOnBattlefield, filterPermanent);
        effect.setText("Starship creatures you control");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new MaintenanceHangarEffect());
        this.addAbility(ability);
    }

    public MaintenanceHangar(final MaintenanceHangar card) {
        super(card);
    }

    @Override
    public MaintenanceHangar copy() {
        return new MaintenanceHangar(this);
    }
}

class RemoveCounterMaintenanceHangarEffect extends OneShotEffect {

    public RemoveCounterMaintenanceHangarEffect() {
        super(Outcome.Detriment);
        staticText = "remove an additional repair counter from each card in your graveyard";
    }

    public RemoveCounterMaintenanceHangarEffect(final RemoveCounterMaintenanceHangarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.getCounters(game).getCount("repair") > 0) {
                    card.removeCounters("repair", 1, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RemoveCounterMaintenanceHangarEffect copy() {
        return new RemoveCounterMaintenanceHangarEffect(this);
    }
}

class MaintenanceHangarEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("Starship creatures");

    static {
        filterCard.add(new SubtypePredicate(SubType.STARSHIP));
    }

    public MaintenanceHangarEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "and starship creatures in your graveyard have Repair 6";
    }

    public MaintenanceHangarEffect(final MaintenanceHangarEffect effect) {
        super(effect);
    }

    @Override
    public MaintenanceHangarEffect copy() {
        return new MaintenanceHangarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cards = controller.getGraveyard().getCards(filterCard, game);
            cards.stream().forEach((card) -> {
                game.getState().addOtherAbility(card, new RepairAbility(6));
            });
            return true;
        }
        return false;
    }
}
