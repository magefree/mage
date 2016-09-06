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
package mage.sets.eldritchmoon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.SplitCardHalf;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TamiyoFieldResearcher extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public TamiyoFieldResearcher(UUID ownerId) {
        super(ownerId, 190, "Tamiyo, Field Researcher", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{W}{U}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Tamiyo");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
        Ability ability = new LoyaltyAbility(new TamiyoFieldResearcherEffect1(), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 2, new FilterCreaturePermanent("creatures"), false));
        this.addAbility(ability);

        // -2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
        ability = new LoyaltyAbility(new TapTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(0, 2, filter, false));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("They"));
        this.addAbility(ability);

        // -7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(3), -7);
        ability.addEffect(new GetEmblemEffect(new TamiyoFieldResearcherEmblem()));
        this.addAbility(ability);
    }

    public TamiyoFieldResearcher(final TamiyoFieldResearcher card) {
        super(card);
    }

    @Override
    public TamiyoFieldResearcher copy() {
        return new TamiyoFieldResearcher(this);
    }
}

class TamiyoFieldResearcherEffect1 extends OneShotEffect {

    public TamiyoFieldResearcherEffect1() {
        super(Outcome.PreventDamage);
        this.staticText = "Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card";
    }

    public TamiyoFieldResearcherEffect1(final TamiyoFieldResearcherEffect1 effect) {
        super(effect);
    }

    @Override
    public TamiyoFieldResearcherEffect1 copy() {
        return new TamiyoFieldResearcherEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<MageObjectReference> creatures = new ArrayList<>();
            for (UUID uuid : getTargetPointer().getTargets(game, source)) {
                creatures.add(new MageObjectReference(uuid, game));
            }
            if (!creatures.isEmpty()) {
                DelayedTriggeredAbility delayedAbility = new TamiyoFieldResearcherDelayedTriggeredAbility(creatures, game.getTurnNum());
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}

class TamiyoFieldResearcherDelayedTriggeredAbility extends DelayedTriggeredAbility {
    
    private int startingTurn;
    private List<MageObjectReference> creatures;

    public TamiyoFieldResearcherDelayedTriggeredAbility(List<MageObjectReference> creatures, int startingTurn) {
        super(new DrawCardSourceControllerEffect(1), Duration.Custom, false);
        this.creatures = creatures;
        this.startingTurn = startingTurn;
     }

    public TamiyoFieldResearcherDelayedTriggeredAbility(final TamiyoFieldResearcherDelayedTriggeredAbility ability) {
        super(ability);
        this.creatures = ability.creatures;
        this.startingTurn = ability.startingTurn;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedEvent;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()) {
            Permanent damageSource = game.getPermanent(event.getSourceId());
            if (damageSource != null) {
                return creatures.contains(new MageObjectReference(damageSource, game));
            }
        }
        return false;
    }
    
    @Override
    public boolean isInactive(Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && game.getTurnNum() != startingTurn;
    }

    @Override
    public TamiyoFieldResearcherDelayedTriggeredAbility copy() {
        return new TamiyoFieldResearcherDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until your next turn, whenever either of those creatures deals combat damage, you draw a card.";
    }
}

class TamiyoFieldResearcherEmblem extends Emblem {
    // You may cast nonland cards from your hand without paying their mana costs.

    public TamiyoFieldResearcherEmblem() {

        this.setName("EMBLEM: Tamiyo, Field Researcher");

        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new TamiyoFieldResearcherCastingEffect()));
    }
}

class TamiyoFieldResearcherCastingEffect extends ContinuousEffectImpl {

    public TamiyoFieldResearcherCastingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may cast nonland cards from your hand without paying their mana costs";
    }

    public TamiyoFieldResearcherCastingEffect(final TamiyoFieldResearcherCastingEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoFieldResearcherCastingEffect copy() {
        return new TamiyoFieldResearcherCastingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(new AlternativeCostSourceAbility(
                    null, new CompoundCondition(SourceIsSpellCondition.getInstance(), new IsBeingCastFromHandCondition()), null, new FilterNonlandCard(), true));
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class IsBeingCastFromHandCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object instanceof SplitCardHalf) {
            UUID splitCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(splitCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return spell.getFromZone() == Zone.HAND;
        }
        if (object instanceof Card) { // needed for the check what's playable
            Card card = (Card) object;
            return game.getPlayer(card.getOwnerId()).getHand().get(card.getId(), game) != null;
        }
        return false;
    }

}
