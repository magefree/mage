package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Cguy7777
 */
public final class CrowdControlWarden extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Other creatures you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));

    public CrowdControlWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Crowd-Control Warden enters the battlefield or is turned face up, put X +1/+1 counters on it, where X is the number of other creatures you control.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new CrowdControlWardenReplacementEffect());
        ability.setWorksFaceDown(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // Disguise {3}{G/W}{G/W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{3}{G/W}{G/W}")));
    }

    private CrowdControlWarden(final CrowdControlWarden card) {
        super(card);
    }

    @Override
    public CrowdControlWarden copy() {
        return new CrowdControlWarden(this);
    }
}

class CrowdControlWardenReplacementEffect extends ReplacementEffectImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE);

    CrowdControlWardenReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "as {this} enters the battlefield or is turned face up, " +
                "put X +1/+1 counters on it, where X is the number of other creatures you control";
    }

    private CrowdControlWardenReplacementEffect(final CrowdControlWardenReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case TURN_FACE_UP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
                if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.TURN_FACE_UP) {
            return event.getTargetId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), xValue, false)
                .apply(game, source);
        return false;
    }

    @Override
    public CrowdControlWardenReplacementEffect copy() {
        return new CrowdControlWardenReplacementEffect(this);
    }
}
