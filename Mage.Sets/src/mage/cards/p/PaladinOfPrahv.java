
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class PaladinOfPrahv extends CardImpl {

    public PaladinOfPrahv(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Paladin of Prahv deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
        
        // Forecast - {1}{W}, Reveal Paladin of Prahv from your hand: Whenever target creature deals damage this turn, you gain that much life.
        Ability ability = new ForecastAbility(new CreateDelayedTriggeredAbilityEffect(
                new PaladinOfPrahvTriggeredAbility()), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PaladinOfPrahv(final PaladinOfPrahv card) {
        super(card);
    }

    @Override
    public PaladinOfPrahv copy() {
        return new PaladinOfPrahv(this);
    }
}

class PaladinOfPrahvTriggeredAbility extends DelayedTriggeredAbility {
    
    public PaladinOfPrahvTriggeredAbility() {
        super(new GainLifeEffect(SavedDamageValue.MUCH), Duration.EndOfTurn, false);
    }

    public PaladinOfPrahvTriggeredAbility(final PaladinOfPrahvTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PaladinOfPrahvTriggeredAbility copy() {
        return new PaladinOfPrahvTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent target = game.getPermanent(this.getFirstTarget());
        if (target != null && event.getSourceId().equals(target.getId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever target creature deals damage this turn, " ;
    }
}
