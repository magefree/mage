package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wanderer120
 */
public final class GreaterHarvester extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("a permanent");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public GreaterHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, sacrifice a permanent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, ""), TargetController.YOU, false));

        //Whenever Greater Harvester deals combat damage to a player, that player sacrifices two permanents.
        this.addAbility(new GreaterHarvesterAbility());
    }

    private GreaterHarvester(final GreaterHarvester card) {
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
        return EventType.DAMAGED_PLAYER == event.getType();
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
