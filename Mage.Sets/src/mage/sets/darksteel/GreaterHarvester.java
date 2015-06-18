/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wanderer120
 */
public class GreaterHarvester extends CardImpl {

    public static final FilterPermanent filter = new FilterPermanent("a permanent");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.PLANESWALKER),
                new CardTypePredicate(CardType.LAND)));
    }

    public GreaterHarvester(UUID ownerId) {
        super(ownerId, 44, "Greater Harvester", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");
        this.expansionSetCode = "DST";
        this.subtype.add("Horror");

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, sacrifice a permanent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, ""), TargetController.YOU, false));

        //Whenever Greater Harvester deals combat damage to a player, that player sacrifices two permanents.
        this.addAbility(new GreaterHarvesterAbility());
    }

    public GreaterHarvester(final GreaterHarvester card) {
        super(card);
    }

    @Override
    public GreaterHarvester copy() {
        return new GreaterHarvester(this);
    }
}

class GreaterHarvesterAbility extends TriggeredAbilityImpl {

    public GreaterHarvesterAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 2, ""));
    }

    public GreaterHarvesterAbility(final GreaterHarvesterAbility ability) {
        super(ability);
    }

    @Override
    public GreaterHarvesterAbility copy() {
        return new GreaterHarvesterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return EventType.DAMAGED_PLAYER.equals(event.getType());
    }

    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player sacrifices two permanent.";
    }
}
