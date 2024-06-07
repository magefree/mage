package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.constants.*;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author grimreap124
 */
public final class RazorfieldRipper extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.ENERGY);

    public RazorfieldRipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Razorfield Ripper or equipped creature attacks, you get {E}, then it gets +X/+X until end of turn, where X is the amount of {E} you have.
        Ability ability = new RazorfieldRipperTriggeredAbility(new GetEnergyCountersControllerEffect(1));
        ability.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn).setText("then it gets +X/+X until end of turn, where X is the amount of {E} you have.").concatBy(","));
        this.addAbility(ability);

        // Reconfigure--Pay {2} or {E}{E}{E}.
        Cost cost = new OrCost("Pay {2} or {E}{E}{E}", new GenericManaCost(2), new PayEnergyCost(3));
        this.addAbility(new ReconfigureAbility(cost));

    }

    private RazorfieldRipper(final RazorfieldRipper card) {
        super(card);
    }

    @Override
    public RazorfieldRipper copy() {
        return new RazorfieldRipper(this);
    }
}

class RazorfieldRipperTriggeredAbility extends TriggeredAbilityImpl {

    RazorfieldRipperTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} or equipped creature attacks, ");
    }

    private RazorfieldRipperTriggeredAbility(final RazorfieldRipperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RazorfieldRipperTriggeredAbility copy() {
        return new RazorfieldRipperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID attacker;
        if (!game.getCombat().getAttackers().contains(getSourceId())) {
            Permanent permanent = getSourcePermanentOrLKI(game);
            if (permanent != null && game.getCombat().getAttackers().contains(permanent.getAttachedTo())) {
                attacker = permanent.getAttachedTo();
            } else {
                attacker = null;
            }
        } else {
            attacker = getSourceId();
        }
        if (attacker == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(attacker, game));
        return true;
    }
}