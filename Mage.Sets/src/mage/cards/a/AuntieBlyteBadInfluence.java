package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.target.common.TargetAnyTarget;
/**
 *
 * @author @stwalsh4118
 */
public final class AuntieBlyteBadInfluence extends CardImpl {

    public AuntieBlyteBadInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source you control deals damage to you, put that many +1/+1 counters on Auntie Blyte, Bad Influence.
        this.addAbility(new AuntieBlyteBadInfluenceTriggeredAbility());

        // {1}{R}, {T}, Remove X +1/+1 counters from Auntie Blyte: It deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(GetXValue.instance, "It"), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AuntieBlyteBadInfluence(final AuntieBlyteBadInfluence card) {
        super(card);
    }

    @Override
    public AuntieBlyteBadInfluence copy() {
        return new AuntieBlyteBadInfluence(this);
    }
}

class AuntieBlyteBadInfluenceTriggeredAbility extends TriggeredAbilityImpl {

    public AuntieBlyteBadInfluenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AuntieBlyteBadInfluenceEffect(), false);
    }

    public AuntieBlyteBadInfluenceTriggeredAbility(final AuntieBlyteBadInfluenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AuntieBlyteBadInfluenceTriggeredAbility copy() {
        return new AuntieBlyteBadInfluenceTriggeredAbility(this);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && sourceControllerId.equals(getControllerId())) {
                getEffects().get(0).setValue("damageAmount", event.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source you controls deals damage to you, put that many +1/+1 counters on {this}.";
    }
}

class AuntieBlyteBadInfluenceEffect extends OneShotEffect {

    public AuntieBlyteBadInfluenceEffect() {
        super(Outcome.BoostCreature);
    }

    public AuntieBlyteBadInfluenceEffect(final AuntieBlyteBadInfluenceEffect effect) {
        super(effect);
    }

    @Override
    public AuntieBlyteBadInfluenceEffect copy() {
        return new AuntieBlyteBadInfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            Integer amount = (Integer) this.getValue("damageAmount");
            if (permanent != null && amount != null && amount > 0) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount), true).apply(game, source);
            }            
            return true;
        }
        return false;
    }
}
