package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.common.FirstXSpellCastThisTurnWatcher;

/**
 *
 * @author muz
 */
public final class NevThePracticalDean extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public NevThePracticalDean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control with counters on them have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("creatures you control with counters on them have trample")));

        // Whenever you cast your first spell with {X} in its mana cost each turn, put X +1/+1 counters on Nev.
        this.addAbility(new NevTriggeredAbility(), new FirstXSpellCastThisTurnWatcher());
    }

    private NevThePracticalDean(final NevThePracticalDean card) {
        super(card);
    }

    @Override
    public NevThePracticalDean copy() {
        return new NevThePracticalDean(this);
    }
}

class NevTriggeredAbility extends TriggeredAbilityImpl {

    NevTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), new EffectKeyValue("xValue", "X")), false);
        setTriggerPhrase("Whenever you cast your first spell with {X} in its mana cost each turn, ");
    }

    private NevTriggeredAbility(final NevTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        FirstXSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstXSpellCastThisTurnWatcher.class);
        if (watcher == null || !spell.getId().equals(watcher.getFirstXSpellId(getControllerId()))) {
            return false;
        }
        int xValue = CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0);
        getEffects().setValue("xValue", xValue);
        return xValue > 0;
    }

    @Override
    public NevTriggeredAbility copy() {
        return new NevTriggeredAbility(this);
    }
}
