
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public final class WallOfLimbs extends CardImpl {

    public WallOfLimbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Whenever you gain life, put a +1/+1 counter on Wall of Limbs.
        this.addAbility(new WallOfLimbsTriggeredAbility());
        
        // {5}{B}{B}, Sacrifice Wall of Limbs: Target player loses X life, where X is Wall of Limbs's power.
        Effect effect = new LoseLifeTargetEffect(new SourcePermanentPowerCount());
        effect.setText("Target player loses X life, where X is {this}'s power.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{5}{B}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private WallOfLimbs(final WallOfLimbs card) {
        super(card);
    }

    @Override
    public WallOfLimbs copy() {
        return new WallOfLimbs(this);
    }
}

class WallOfLimbsTriggeredAbility extends TriggeredAbilityImpl {
    
    WallOfLimbsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }
    
    WallOfLimbsTriggeredAbility(final WallOfLimbsTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public WallOfLimbsTriggeredAbility copy() {
        return new WallOfLimbsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }
    
    @Override
    public String getRule() {
        return "Whenever you gain life, put a +1/+1 counter on Wall of Limbs.";
    }
}
