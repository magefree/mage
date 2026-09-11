package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class JiangYangguAlone extends CardImpl {

    public JiangYangguAlone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature you control attacks a player alone, discard a card, then draw a card. Then put a +1/+1 counter on that creature for each card you've discarded this turn.
        Ability ability = new JiangYangguAloneTriggeredAbility();
        this.addAbility(ability.addHint(JiangYangguAloneValue.getHint()), new DiscardedCardWatcher());
    }

    private JiangYangguAlone(final JiangYangguAlone card) {
        super(card);
    }

    @Override
    public JiangYangguAlone copy() {
        return new JiangYangguAlone(this);
    }
}

class JiangYangguAloneTriggeredAbility extends TriggeredAbilityImpl {

    JiangYangguAloneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardControllerEffect(1));
        this.addEffect(new DrawCardSourceControllerEffect(JiangYangguAloneValue.instance).concatBy("then"));
        this.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), JiangYangguAloneValue.instance)
                .setText("then put a +1/+1 counter on that creature for each card you've discarded this turn"));
        this.setTriggerPhrase("Whenever a creature you control attacks a player alone, ");
    }

    private JiangYangguAloneTriggeredAbility(final JiangYangguAloneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JiangYangguAloneTriggeredAbility copy() {
        return new JiangYangguAloneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        UUID defenderId = game.getCombat().getDefenderId(permanent.getId());
        if (defenderId == null || game.getPlayer(defenderId) == null) {
            return false;
        }
        if (game.getCombat().getAttackers().stream()
                .map(game.getCombat()::getDefenderId)
                .filter(defenderId::equals)
                .count() != 1) {
            return false;
        }
        this.getAllEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}

enum JiangYangguAloneValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Cards you've discarded this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public JiangYangguAloneValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "card you've discarded this turn";
    }
}
