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
package mage.sets.starwars;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class MaintenanceHangar extends CardImpl {

    private static final FilterCreaturePermanent filterPermanent = new FilterCreaturePermanent("Starship creatures");

    static {
        filterPermanent.add(new SubtypePredicate("Starship"));
    }

    public MaintenanceHangar(UUID ownerId) {
        super(ownerId, 23, "Maintenance Hangar", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "SWS";

        // At the beggining of your upkeep, remove an additional repair counter from each card in your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterMaintenanceHangarEffect(), TargetController.YOU, false));

        // Starship creatures you control and starship creatures in your graveyard have Repair 6.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new RepairAbility(6), Duration.WhileOnBattlefield, filterPermanent)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaintenanceHangarEffect()));

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

    private static final FilterCard filterCard = new FilterCard("each card");

    static {
        filterCard.add(new CounterPredicate(CounterType.REPAIR));
    }

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
            Set<Card> cards = controller.getGraveyard().getCards(filterCard, game);
            for (Card card : cards) {
                if (card != null) {
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
        filterCard.add(new SubtypePredicate("Starship"));
    }

    public MaintenanceHangarEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Starship creatures in your graveyard have Repair 6";
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
            for (Card card : cards) {
                if (card != null) {
                    RepairAbility ability = new RepairAbility(6);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            }
            return true;
        }
        return false;
    }
}
